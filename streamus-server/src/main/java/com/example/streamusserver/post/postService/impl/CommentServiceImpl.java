package com.example.streamusserver.post.postService.impl;

import com.example.streamusserver.exception.PostNotFoundException;
import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.notification.service.NotificationService;
import com.example.streamusserver.post.dto.request.CommentRequestDto;
import com.example.streamusserver.post.dto.response.CommentRepliesResponse;
import com.example.streamusserver.post.dto.response.CommentResponseDto;
import com.example.streamusserver.post.model.Comment;
import com.example.streamusserver.post.model.Post;
import com.example.streamusserver.post.postService.CommentService;
import com.example.streamusserver.post.repository.CommentRepository;
import com.example.streamusserver.post.repository.PostRepository;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final JwtUtil jwtUtil;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private NotificationService notificationService;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentDTO) {

        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() ->
                        new PostNotFoundException(commentDTO.getPostId()));

        UserProfile user = userProfileService.findById(commentDTO.getAccountId())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(commentDTO.getCommentText());

        Comment parentComment = null;

        // Reply logic
        if (commentDTO.getParentCommentId() != null) {

            parentComment = commentRepository
                    .findById(commentDTO.getParentCommentId())
                    .orElseThrow(() ->
                            new RuntimeException("Parent comment not found"));

            comment.setParentComment(parentComment);

            // Root-ը որոշում ենք առանց ծառով քայլելու.
            // Եթե parent-ն ինքն արդեն ունի rootId, ուրեմն reply-ն էլ նույն root-ին է պատկանում,
            // հակառակ դեպքում parent-ը ինքն է root-ը
            Long rootId = (parentComment.getRootId() != null)
                    ? parentComment.getRootId()
                    : parentComment.getId();

            comment.setRootId(rootId);

            // Immediate parent-ի repliesCount-ը թարմացնում ենք
            parentComment.setRepliesCount(parentComment.getRepliesCount() + 1);
            commentRepository.save(parentComment);

            // Root comment-ի repliesCount-ը թարմացնում ենք
            // (եթե parent-ը ինքն է root-ը, չկրկնվի save-ը)
            if (!rootId.equals(parentComment.getId())) {
                Comment rootComment = commentRepository.findById(rootId)
                        .orElseThrow(() ->
                                new RuntimeException("Root comment not found"));
                rootComment.setRepliesCount(rootComment.getRepliesCount() + 1);
                commentRepository.save(rootComment);
            }
        }

        // Increase comments count
        post.setCommentsCount(post.getCommentsCount() + 1);
        postRepository.save(post);

        // Save comment
        Comment savedComment = commentRepository.save(comment);

        // ---------------- Notifications ----------------

        Set<Long> notifiedUsers = new HashSet<>();

        // Notify post owner
        Long postOwnerId = post.getAccount().getId();

        if (!postOwnerId.equals(user.getId())) {

            notificationService.createCommentNotification(
                    user,
                    post,
                    commentDTO.getCommentText()
            );

            notifiedUsers.add(postOwnerId);
        }

        // Notify all parent comment owners
        if (parentComment != null) {

            Comment current = parentComment;

            while (current != null) {

                Long receiverId = current.getUser().getId();

                if (!receiverId.equals(user.getId())
                        && !notifiedUsers.contains(receiverId)) {

                    notificationService.createReplyNotification(
                            user,
                            current.getUser(),
                            post,
                            savedComment,
                            commentDTO.getCommentText()
                    );

                    notifiedUsers.add(receiverId);
                }

                current = current.getParentComment();
            }
        }

        return mapToDTO(savedComment);
    }    @Transactional(readOnly = true)
    public Page<CommentResponseDto> getCommentsByPostId(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> comments = commentRepository.findByPostIdAndParentCommentIsNullOrderByCreatedAtDesc(postId, pageable);
        return comments.map(this::mapToDTO);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPostId(CommentRequestDto commentRequestDto) {
   List<Comment> comments = commentRepository.findByPostIdAndParentCommentIsNullOrderByCreatedAtDesc(commentRequestDto.getPostId());
        List<CommentResponseDto> commentResponseDtos = comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

        return commentResponseDtos;
    }

    @Override
    public void deleteAll(List<Comment> commentsByPostId) {
        commentRepository.deleteAll(commentsByPostId);
    }

    @Transactional(readOnly = true)
    public CommentRepliesResponse getRepliesForComment(Long commentId, Long accountId, String accessToken) {

        CommentRepliesResponse response = new CommentRepliesResponse();

        // optional auth check (եթե ունես token system)
        if (accountId == null || accessToken == null) {
            response.setError(true);
            return response;
        }

        List<Comment> replies =
                commentRepository.findAllByRootId(commentId);
        List<CommentResponseDto> commentResponseDtos = replies.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
        response.setError(false);
        response.setReplies(commentResponseDtos);

        return response;
    }

    @Override
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

//    @Transactional
//    public CommentResponseDto updateComment(Long commentId, Long userId, String content) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new CommentNotFoundException(commentId));
//
//        if (!jwtUtil.isTokenValid(request.getAccessToken())) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get post Items ");
//        }
//
//        comment.setContent(content);
//        comment.setEdited(true);
//        Comment updatedComment = commentRepository.save(comment);
//        return mapToDTO(updatedComment);
//    }

//    @Transactional
//    public void deleteComment(Long commentId, Long userId) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new CommentNotFoundException(commentId));
//
//        if (!jwtUtil.isTokenValid(request.getAccessToken())) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get post Items ");
//        }
//
//        commentRepository.delete(comment);
//    }


    @Transactional(readOnly = true)
    public int getCommentCount(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    private CommentResponseDto mapToDTO(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setItemId(comment.getPost().getId());
        dto.setUserId(comment.getUser().getId());
        dto.setUsername(comment.getUser().getUsername());
        dto.setUserAvatar(comment.getUser().getPhotoUrl());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setLikeCount(comment.getLikesCount());
        dto.setEdited(comment.isEdited());
        dto.setReplyCount(comment.getRepliesCount());
        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getId());
        }

        return dto;
    }
}