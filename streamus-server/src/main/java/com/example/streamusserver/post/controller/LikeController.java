package com.example.streamusserver.post.controller;

import com.example.streamusserver.post.dto.LikeResponse;
import com.example.streamusserver.post.postService.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/toggle")
    public ResponseEntity<LikeResponse> toggleLike(
            @RequestParam Long userId,
            @RequestParam Long postId) {

        return ResponseEntity.ok(likeService.toggleLike(userId, postId));
    }

    @GetMapping("/status")
    public ResponseEntity<LikeResponse> getLikeStatus(
            @RequestParam Long userId,
            @RequestParam Long postId) {

        return ResponseEntity.ok(likeService.getLike(userId, postId));
    }
}
