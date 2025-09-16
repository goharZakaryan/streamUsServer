package com.example.streamusserver.post.controller;

import com.example.streamusserver.post.dto.request.HideItemRequestDto;
import com.example.streamusserver.post.dto.request.PostRequestDto;
import com.example.streamusserver.post.dto.request.StreamRequestDto;
import com.example.streamusserver.post.dto.response.PostResponseDto;
import com.example.streamusserver.post.dto.response.StreamResponseDto;
import com.example.streamusserver.post.dto.response.UploadResponseDto;
import com.example.streamusserver.post.model.Post;
import com.example.streamusserver.post.postService.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    @PostMapping("/post/delete")
    public ResponseEntity<Void> hidePost(@RequestBody HideItemRequestDto hideItemRequestDto) {
        postService.deletePost(hideItemRequestDto);
        return ResponseEntity.ok().build();
    }

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

    @PostMapping("/stream/get")
    public ResponseEntity<StreamResponseDto> streamGet(@RequestBody StreamRequestDto postRequest) {
        System.out.println("savepost");
        StreamResponseDto response = postService.getItems(postRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/items")
    public ResponseEntity<StreamResponseDto> getItems(@RequestBody StreamRequestDto request) {
        StreamResponseDto response = postService.getItems(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/items/{itemId}/repost")
    public ResponseEntity<Boolean> repost(
            @PathVariable Long itemId,
            @RequestParam String userId) {
        boolean success = postService.updateRepost(itemId, userId);
        return ResponseEntity.ok(success);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<Post> updateItem(
            @PathVariable Long itemId,
            @RequestBody Post item) {
        if (!itemId.equals(item.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Post updatedItem = postService.updateItem(item);
        return ResponseEntity.ok(updatedItem);
    }

    @PostMapping("/upload/file")
    public ResponseEntity<UploadResponseDto> uploadFile(
            @RequestParam("uploaded_file") MultipartFile file,
            @RequestParam("accountId") String accountId,
            @RequestParam("accessToken") String accessToken) {
        return ResponseEntity.ok(postService.uploadedFile(file, Long.parseLong(accountId), accessToken));

    }

    @PostMapping("/upload/music")
    public ResponseEntity<UploadResponseDto> uploadMusic(
            @RequestParam("uploaded_music") MultipartFile file,
            @RequestParam("accountId") String accountId,
            @RequestParam("accessToken") String accessToken)  {
        System.out.println("music");
        return ResponseEntity.ok(postService.uploadedFile(file, Long.parseLong(accountId), accessToken));

    }

    @PostMapping("/upload/video")
    public ResponseEntity<UploadResponseDto> uploadVideo(
            @RequestParam("uploaded_video_file") MultipartFile videoFile,
            @RequestParam("uploaded_file") MultipartFile file,
            @RequestParam("accountId") String accountId,
            @RequestParam("accessToken") String accessToken) {
        return ResponseEntity.ok(postService.uploadedVideoFile(videoFile, file, Long.parseLong(accountId), accessToken));

    }

}
