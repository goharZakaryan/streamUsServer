package com.example.streamusserver.post.postService;

import com.example.streamusserver.post.dto.request.PostRequestDto;
import com.example.streamusserver.post.dto.request.StreamRequestDto;
import com.example.streamusserver.post.dto.response.PostResponseDto;
import com.example.streamusserver.post.dto.response.StreamResponseDto;
import com.example.streamusserver.post.dto.response.UploadResponseDto;
import com.example.streamusserver.post.model.Post;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    Post updateItem(Post item);
    boolean updateRepost(Long itemId, String userId);
    StreamResponseDto getItems(StreamRequestDto request);
    PostResponseDto savePostToDatabase(PostRequestDto postRequest);
    PostResponseDto editPost(PostRequestDto postRequest);
    UploadResponseDto uploadedFile(MultipartFile file, Long accountId, String accessToken);

    UploadResponseDto uploadedVideoFile(MultipartFile videoFile, MultipartFile file, long l, String accessToken);
}
