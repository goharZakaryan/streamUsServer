package com.example.streamusserver.post.postService;

import com.example.streamusserver.post.dto.PostRequestDto;
import com.example.streamusserver.post.dto.PostResponseDto;
import com.example.streamusserver.post.dto.UploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    PostResponseDto savePostToDatabase(PostRequestDto postRequest);
    PostResponseDto editPost(PostRequestDto postRequest);
    UploadResponseDto saveUploadedFile(MultipartFile file, Long accountId, String accessToken);

}
