package com.example.streamusserver.post.postService.impl;

import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.dto.request.PostRequestDto;
import com.example.streamusserver.post.dto.request.StreamRequestDto;
import com.example.streamusserver.post.dto.response.PostResponseDto;
import com.example.streamusserver.post.dto.response.StreamResponseDto;
import com.example.streamusserver.post.dto.response.UploadResponseDto;
import com.example.streamusserver.post.mapper.PostImageMapper;
import com.example.streamusserver.post.mapper.PostMapper;
import com.example.streamusserver.post.model.MediaItem;
import com.example.streamusserver.post.model.Post;
import com.example.streamusserver.post.postService.PostService;
import com.example.streamusserver.post.repository.MediaItemRepository;
import com.example.streamusserver.post.repository.PostRepository;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.UserProfileService;
import jakarta.servlet.ServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    private final MediaItemRepository mediaItemRepository;
    private final PostMapper postMapper;
    private final PostImageMapper postImageMapper;
    private final ServletRequest serverRequest;

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

//        Post post = postMapper.toEntity(postRequest, authenticatedUser);
//        post.setMediaItem(postRequest.getMediaItems());
//
//        postRepository.save(post);

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
        List<MediaItem> postImages = postRequest.getMediaItems().stream()
                .map(imageUrl -> {
                    MediaItem mediaItem = new MediaItem();
                    mediaItem.setImageUrl(imageUrl.getImageUrl());
                    mediaItem.setVideoUrl(imageUrl.getVideoUrl() != null ? imageUrl.getVideoUrl() : "");
                    mediaItem.setType(imageUrl.getType());
                    return mediaItem;
                })
                .collect(Collectors.toList());
        mediaItemRepository.saveAll(postImages);
        post.setMediaItem(postImages);
        postRepository.save(post);
        return response;
    }


    @Override
    public UploadResponseDto uploadedFile(MultipartFile file, Long accountId, String accessToken) {
        if (!jwtUtil.isTokenValid(accessToken)) {
            return new UploadResponseDto(true, "Invalid access token", null);
        }

        String imageUrl = "public/" + saveFile(file, accountId);

        return new UploadResponseDto(false, "Upload successful", imageUrl);
    }

    @Override
    public UploadResponseDto uploadedVideoFile(MultipartFile videoFile, MultipartFile file, long l, String accessToken) {
        if (!jwtUtil.isTokenValid(accessToken)) {
            return new UploadResponseDto(true, "Invalid access token", null);
        }

        String imageUrl = "public/" + saveFile(file, l);
        String videoUrl = "public/" + saveFile(videoFile, l);

        return new UploadResponseDto(false, "Upload successful", imageUrl, videoUrl);

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

    private String saveFile(MultipartFile file, Long accountId) {
        if (file == null || file.isEmpty()) {
            return "";
        }

        try {
            // Ensure directory exists
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            // Generate unique filename
            String fileName = System.currentTimeMillis() + "_" + accountId + "_" + file.getOriginalFilename();
            Path targetLocation = uploadPath.resolve(fileName);

            // Copy file to target location
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + file.getOriginalFilename(), ex);
        }

    }

    public StreamResponseDto getItems(StreamRequestDto request) {
        StreamResponseDto response = new StreamResponseDto();

        if (!jwtUtil.isTokenValid(request.getAccessToken())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get post Items ");
        }

        // Get items with pagination
        Pageable pageable = PageRequest.of(0, request.getLimit());
        List<Post> items;

        if (request.getItemId() == 0) {
            // Fresh load or refresh
            items = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        } else {
            // Load more (pagination)
            items = postRepository.findByIdLessThanOrderByCreatedAtDesc(
                    Long.valueOf(request.getItemId()),
                    pageable
            );
        }

        // Set response
        response.setError(false);
        response.setItems(items);
        response.setViewMore(items.size() >= request.getLimit());

        // Set last item ID for next pagination
        if (!items.isEmpty()) {
            response.setItemId(Integer.parseInt(String.valueOf(items.get(items.size() - 1).getId())));
        }

        return response;
    }

    public boolean updateRepost(Long itemId, String userId) {
        Post item = postRepository.findById(itemId).orElse(null);
        if (item != null) {
            item.setRePostsCount(item.getRePostsCount() + 1);
            postRepository.save(item);
            return true;
        }
        return false;
    }

    public Post updateItem(Post item) {
        return postRepository.save(item);
    }
}

