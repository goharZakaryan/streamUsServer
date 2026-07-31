package com.example.streamusserver.post.postService.impl;

import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.dto.request.HideItemRequestDto;
import com.example.streamusserver.post.model.Comment;
import com.example.streamusserver.post.model.HideComment;
import com.example.streamusserver.post.postService.CommentService;
import com.example.streamusserver.post.postService.HiddenCommentService;
import com.example.streamusserver.post.repository.HiddenCommentRepository;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class HiddenCommentServiceImpl implements HiddenCommentService {


    @Autowired
    private HiddenCommentRepository hiddenCommentRepository;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public void hidePost(HideItemRequestDto itemRequestDto) {
        if (!jwtUtil.isTokenValid(itemRequestDto.getAccessToken())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get post Items ");
        }
        UserProfile user = userProfileService.findById(itemRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(itemRequestDto.getUserId().toString()));
        Comment comment = commentService.findById(itemRequestDto.getPostId());


        HideComment hiddenContent = new HideComment();
        hiddenContent.setUser(user);
        hiddenContent.setPost(comment);
        hiddenContent.setHiddenAt(LocalDateTime.now());

        hiddenCommentRepository.save(hiddenContent);
    }

    @Transactional
    public void unHidePost(String username, Long postId) {

        UserProfile user = userProfileService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        Comment comment = commentService.findById(postId);

        hiddenCommentRepository.deleteByUserAndComment(user, comment);
    }
}
