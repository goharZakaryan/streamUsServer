package com.example.streamusserver.post.controller;

import com.example.streamusserver.post.dto.PostRequestDto;
import com.example.streamusserver.post.dto.PostResponseDto;
import com.example.streamusserver.post.dto.UploadResponseDto;
import com.example.streamusserver.post.postService.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    @PostMapping("/edit")
    public ResponseEntity<PostResponseDto> editPost(@RequestBody PostRequestDto postRequest) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(postService.editPost(postRequest));

    }


    @PostMapping("/post/save")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequest) {
        System.out.println("savepost");
        PostResponseDto response = postService.savePostToDatabase(postRequest);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/upload/file")
    public ResponseEntity<UploadResponseDto> uploadFile(
            @RequestParam("uploaded_file") MultipartFile file,
            @RequestParam("accountId") String accountId,
            @RequestParam("accessToken") String accessToken) {
        return ResponseEntity.ok(postService.saveUploadedFile(file, Long.parseLong(accountId), accessToken));

    }

}
