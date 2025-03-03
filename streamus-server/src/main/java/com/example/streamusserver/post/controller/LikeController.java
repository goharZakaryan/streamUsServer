package com.example.streamusserver.post.controller;

import com.example.streamusserver.post.dto.LikeResponse;
import com.example.streamusserver.post.postService.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getLikeStatus(
            @RequestParam Long userId,
            @RequestParam Long postId) {
        boolean isLiked = likeService.checkIfUserLikedPost(userId, postId);
        int likeCount = likeService.getLikeCount(postId);

        Map<String, Object> response = new HashMap<>();
        response.put("liked", isLiked);
        response.put("likeCount", likeCount);

        return ResponseEntity.ok(response);
    }
}
