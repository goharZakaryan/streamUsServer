package com.example.streamusserver.post.postService;

import com.example.streamusserver.post.dto.PostRequestDto;
import com.example.streamusserver.post.dto.PostResponseDto;

public interface PostService {
    PostResponseDto editPost(PostRequestDto postRequest);
}
