package com.example.streamusserver.post.controller;

import com.example.streamusserver.post.dto.response.LikeResponse;
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

    @PostMapping("/story")
    public ResponseEntity<LikeResponse> likeStory(
            @RequestParam Long userId,
            @RequestParam Long storyId) {

        return ResponseEntity.ok(likeService.likeStory(userId, storyId));
    }

    @GetMapping("/status")
    public ResponseEntity<LikeResponse> getLikeStatus(
            @RequestParam Long userId,
            @RequestParam Long postId) {

        return ResponseEntity.ok(likeService.getLike(userId, postId));
    }

    @GetMapping("/story/isLike")
    public ResponseEntity<Boolean> isLike(
            @RequestParam Long userId,
            @RequestParam Long storyId) {

        return ResponseEntity.ok(likeService.checkIfUserLikedStory(userId, storyId));
    }
}
