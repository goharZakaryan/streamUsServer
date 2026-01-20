package com.example.streamusserver.post.postService.impl;

import com.example.streamusserver.exception.PostNotFoundException;
import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.notification.service.NotificationService;
import com.example.streamusserver.post.dto.request.HideItemRequestDto;
import com.example.streamusserver.post.dto.request.PostRequestDto;
import com.example.streamusserver.post.dto.request.StreamRequestDto;
import com.example.streamusserver.post.dto.response.PostResponseDto;
import com.example.streamusserver.post.dto.response.StreamResponseDto;
import com.example.streamusserver.post.dto.response.UploadResponseDto;
import com.example.streamusserver.post.mapper.PostMap;
import com.example.streamusserver.post.model.MediaItem;
import com.example.streamusserver.post.model.Post;
import com.example.streamusserver.post.model.enums.ImageType;
import com.example.streamusserver.post.postService.CommentService;
import com.example.streamusserver.post.postService.PostService;
import com.example.streamusserver.post.repository.MediaItemRepository;
import com.example.streamusserver.post.repository.PostRepository;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final JwtUtil jwtUtil;
    @Value("${file.path}")
    private String uploadDir;
    @Autowired

    private UserProfileService userProfileService;
    private final PostRepository postRepository;
    private final MediaItemRepository mediaItemRepository;
    @Autowired
    private NotificationService notificationService;
    private final PostMap postMapper;
    @Autowired
    private CommentService commentService;

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

    @Transactional
    public void deletePost(HideItemRequestDto itemRequestDto) {
        if (!jwtUtil.isTokenValid(itemRequestDto.getAccessToken())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to get post Items ");
        }
        UserProfile user = userProfileService.findById(itemRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(itemRequestDto.getUserId().toString()));
        Post post = findById(itemRequestDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(itemRequestDto.getPostId()));

        if (itemRequestDto.getType().equalsIgnoreCase(ImageType.PHOTO.name())) {
            MediaItem mediaItem = mediaItemRepository.findByImageUrl(itemRequestDto.getItemUrl());

            List<MediaItem> mediaList = post.getMediaItem();

            boolean removed = mediaList.remove(mediaItem);
            if (removed) {
                post.setMediaItem(mediaList);
                if (!mediaList.isEmpty()) {

                    postRepository.save(post); // Update the post

                } else {
                    commentService.deleteAll(commentService.getCommentsByPostId(post.getId()));
                   notificationService.deleteAllByPost(post);
                    postRepository.delete(post);
                }
                mediaItemRepository.delete(mediaItem); // Delete from DB
            }
        } else {
            mediaItemRepository.deleteByVideoUrl(itemRequestDto.getItemUrl());
        }
    }

    @Transactional
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
                    mediaItem.setAudioUrl(imageUrl.getAudioUrl() != null ? imageUrl.getAudioUrl() : "");
                    mediaItem.setType(imageUrl.getType());
                    mediaItem.setPost(post);
                    return mediaItem;
                })
                .collect(Collectors.toList());
        mediaItemRepository.saveAll(postImages);
        post.setMediaItem(postImages);
        post.setCreatedAt(LocalDate.now());
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
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
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

    public String saveFile(MultipartFile file, Long accountId) {
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
            items = postRepository.findAllByOrderByCreatedAtDesc(request.getAccountId(), pageable);
        } else {
            // Load more (pagination)
            items = postRepository.findByIdLessThanOrderByCreatedAtDesc(
                    Long.valueOf(request.getItemId()),
                    pageable
            );
        }


        // Set response
        response.setError(false);
        response.setItems(postMapper.converttoDto(items));
        response.setNotification(notificationService.notificationExists(request.getAccountId()));
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

