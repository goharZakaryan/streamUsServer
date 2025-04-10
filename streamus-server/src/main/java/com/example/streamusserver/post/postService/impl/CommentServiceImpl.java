package com.example.streamusserver.post.postService.impl;

import com.example.streamusserver.exception.CommentNotFoundException;
import com.example.streamusserver.exception.PostNotFoundException;
import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.notification.service.NotificationService;
import com.example.streamusserver.post.dto.request.CommentRequestDto;
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

import java.util.List;
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
                .orElseThrow(() -> new PostNotFoundException(commentDTO.getPostId()));

        UserProfile user = userProfileService.findById(commentDTO.getAccountId())
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(commentDTO.getCommentText());
        comment.setUser(user);
//        if (commentDTO.getParentCommentId() != null) {
//            Comment parentComment = commentRepository.findById(commentDTO.getParentCommentId())
//                    .orElseThrow(() -> new CommentNotFoundException(commentDTO.getParentCommentId()));
//            comment.setParentComment(parentComment);
//        }
        int commentsCount=post.getComments().size();
        post.setCommentsCount(++commentsCount);
        postRepository.save(post);
        Comment savedComment = commentRepository.save(comment);
        if (!user.equals(post.getAccount().getId())){
            notificationService.createCommentNotification(user,post,commentDTO.getCommentText());

        }
        return mapToDTO(savedComment);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> getCommentsByPostId(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> comments = commentRepository.findByPostIdAndParentCommentIsNullOrderByCreatedAtDesc(postId, pageable);
        return comments.map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPostId(CommentRequestDto commentRequestDto) {
        List<Comment> comments = commentRepository.findByPostId(commentRequestDto.getPostId());
        List<CommentResponseDto> commentResponseDtos = comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

        return commentResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getRepliesForComment(Long commentId) {
        List<Comment> replies = commentRepository.findByParentCommentIdOrderByCreatedAtAsc(commentId);
        return replies.stream().map(this::mapToDTO).collect(Collectors.toList());
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
        dto.setPostId(comment.getPost().getId());
        dto.setUserId(comment.getUser().getId());
        dto.setUsername(comment.getUser().getUsername());
        dto.setUserAvatar(comment.getUser().getPhoto_url());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        dto.setLikeCount(comment.getLikesCount());
        dto.setEdited(comment.isEdited());

        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getId());
        }

        return dto;
    }
}