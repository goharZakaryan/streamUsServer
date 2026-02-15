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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;
    @Value("${file.path}")
    private String uploadDir;

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

    @GetMapping("/media/audio/{fileName:.+}")  // ⭐ Հեռացված /public/
    public ResponseEntity<Resource> streamAudio(
            @PathVariable String fileName,
            @RequestHeader(value = "Authorization", required = false) String authHeader)
            throws IOException {

        // ⭐ fileName-ը արդեն decoded է Spring-ի կողմից
        File file = new File(uploadDir, fileName);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);

        // Որոշիր content type
        String contentType = "audio/mpeg";
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (fileExtension) {
            case "mp3":
                contentType = "audio/mpeg";
                break;
            case "mp4":
            case "m4a":
                contentType = "audio/mp4";
                break;
            case "wav":
                contentType = "audio/wav";
                break;
            case "ogg":
                contentType = "audio/ogg";
                break;
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
                .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                .header(HttpHeaders.CACHE_CONTROL, "public, max-age=31536000")
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .body(resource);
    }
}
