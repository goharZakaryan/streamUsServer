package com.example.streamusserver.post.postService.impl;

import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.dto.PostRequestDto;
import com.example.streamusserver.post.dto.PostResponseDto;
import com.example.streamusserver.post.dto.UploadResponseDto;
import com.example.streamusserver.post.mapper.PostImageMapper;
import com.example.streamusserver.post.mapper.PostMapper;
import com.example.streamusserver.post.model.Post;
import com.example.streamusserver.post.model.PostImage;
import com.example.streamusserver.post.postService.PostService;
import com.example.streamusserver.post.repository.PostImageRepository;
import com.example.streamusserver.post.repository.PostRepository;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.UserProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final JwtUtil jwtUtil;
    @Value("${file.path}")
    private String uploadDir;
    private final UserProfileService userProfileService;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
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

    public PostResponseDto savePostToDatabase(PostRequestDto postRequest) {
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
        postImageRepository.saveAll(postImages);
        post.setImages(postImages);
        postRepository.save(post);
        return response;
    }


    @Override
    public UploadResponseDto saveUploadedFile(MultipartFile file, Long accountId, String accessToken) {
        if (!jwtUtil.isTokenValid(accessToken)) {
            return new UploadResponseDto(true, "Invalid access token", null);
        }
        UserProfile authenticatedUser = userProfileService.findById(accountId)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));

        String imageUrl = null;
        try {
            imageUrl = saveFile(file, accountId);
            Post post = new Post();
            post.setAccount(authenticatedUser);

            post.setVideoImgUrl(imageUrl);

            // Process and add images

            PostImage postImage = new PostImage();
            postImage.setPost(post);
            postImage.setImageUrl(imageUrl);
            List<PostImage> postImages = new ArrayList<>();
            postImages.add(postImage);
            post.setImages(postImages);

            postRepository.save(post);

            postImageRepository.saveAll(postImages);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new UploadResponseDto(false, "Upload successful", imageUrl);
    }

    private PostResponseDto createSuccessResponse() {
        PostResponseDto response = new PostResponseDto();
        response.setError(false);
        response.setMessage("Post edited successfully");
        return response;
    }

    private PostResponseDto createErrorResponse(String message) {
        PostResponseDto response = new PostResponseDto();
        response.setError(true);
        response.setMessage(message);
        return response;
    }

    private String saveFile(MultipartFile file, Long accountId) throws IOException {
        if (file == null || file.isEmpty()) {
            return "";
        }

        try {
            // Ensure directory exists
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            // Generate unique filename
            String fileName = System.currentTimeMillis() + "_" + accountId+"_" + file.getOriginalFilename() ;
            Path targetLocation = uploadPath.resolve(fileName);

            // Copy file to target location
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + file.getOriginalFilename(), ex);
        }

    }
}

