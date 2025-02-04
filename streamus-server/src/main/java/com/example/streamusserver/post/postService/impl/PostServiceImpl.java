package com.example.streamusserver.post.postService.impl;

import com.example.streamusserver.exception.UnauthorizedAccessException;
import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.dto.PostRequestDto;
import com.example.streamusserver.post.dto.PostResponseDto;
import com.example.streamusserver.post.mapper.PostImageMapper;
import com.example.streamusserver.post.mapper.PostMapper;
import com.example.streamusserver.post.model.Post;
import com.example.streamusserver.post.model.PostImage;
import com.example.streamusserver.post.postService.PostService;
import com.example.streamusserver.post.repository.PostRepository;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.UserProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final JwtUtil jwtUtil;
    private final UserProfileService userProfileService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostImageMapper postImageMapper;

    @Transactional
    @Override
    public PostResponseDto editPost(PostRequestDto postRequest) {
        // Token validation
        if (!jwtUtil.isTokenValid(postRequest.getAccessToken())) {
            return createErrorResponse("Invalid access token");
        }

        // Fetch user
        UserProfile authenticatedUser = userProfileService.findById(postRequest.getAccountId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Post post = postMapper.toEntity(postRequest, authenticatedUser);
        post.setImages(postImageMapper.mapImageUrls(postRequest.getImages(), post));

        postRepository.save(post);

        return createSuccessResponse();
    }

    private PostResponseDto savePostToDatabase(PostRequestDto postRequest) {
        PostResponseDto response = new PostResponseDto();
        UserProfile authenticatedUser = userProfileService.findById(postRequest.getAccountId())
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));
        if (!jwtUtil.isTokenValid(postRequest.getAccessToken())) {
            response.setError(true);
            response.setMessage("Invalid access token");
            return response;
        }

        Post post = new Post();
        post.setAccount(authenticatedUser);
        post.setPostText(postRequest.getPostText());
        post.setVideoUrl(postRequest.getVideoUrl());
        post.setVideoImgUrl(postRequest.getVideoImgUrl());

        // Process and add images
        List<PostImage> postImages = postRequest.getImages().stream()
                .map(imageUrl -> {
                    PostImage postImage = new PostImage();
                    postImage.setPost(post);
                    postImage.setImageUrl(imageUrl);
                    return postImage;
                })
                .collect(Collectors.toList());

        post.setImages(postImages);
        postRepository.save(post);
        return response;
    }

    private PostResponseDto createErrorResponse(String message) {
        PostResponseDto response = new PostResponseDto();
        response.setError(true);
        response.setMessage(message);
        return response;
    }



    private PostResponseDto createSuccessResponse() {
        PostResponseDto response = new PostResponseDto();
        response.setError(false);
        response.setMessage("Post edited successfully");
        return response;
    }
}

