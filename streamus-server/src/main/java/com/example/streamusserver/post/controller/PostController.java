package com.example.streamusserver.post.controller;

import com.example.streamusserver.post.dto.PostRequestDto;
import com.example.streamusserver.post.dto.PostResponseDto;
import com.example.streamusserver.post.postService.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;
    @PostMapping("/edit")
    public ResponseEntity<PostResponseDto> editPost(@RequestBody PostRequestDto postRequest) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(postService.editPost(postRequest));

    }


}
