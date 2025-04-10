package com.example.streamusserver.post.postService.impl;

import com.example.streamusserver.exception.PostNotFoundException;
import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.dto.request.HideItemRequestDto;
import com.example.streamusserver.post.model.HidePost;
import com.example.streamusserver.post.model.Post;
import com.example.streamusserver.post.postService.HiddenPostService;
import com.example.streamusserver.post.postService.PostService;
import com.example.streamusserver.post.repository.HiddenPostRepository;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class HiddenPostServiceImpl implements HiddenPostService {


    @Autowired
    private HiddenPostRepository hiddenContentRepository;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private PostService postService;
    @Autowired
    private  JwtUtil jwtUtil;

    @Transactional
    public void hidePost(HideItemRequestDto itemRequestDto) {
        if (!jwtUtil.isTokenValid(itemRequestDto.getAccessToken())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get post Items ");
        }
        UserProfile user = userProfileService.findById(itemRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(itemRequestDto.getUserId().toString()));
        Post post = postService.findById(itemRequestDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(itemRequestDto.getPostId()));


        HidePost hiddenContent = new HidePost();
        hiddenContent.setUser(user);
        hiddenContent.setPost(post);
        hiddenContent.setHiddenAt(LocalDateTime.now());

        hiddenContentRepository.save(hiddenContent);
    }

    @Transactional
    public void unHidePost(String username, Long postId) {

        UserProfile user = userProfileService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        Post post = postService.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        hiddenContentRepository.deleteByUserAndPost(user, post);
    }
}
