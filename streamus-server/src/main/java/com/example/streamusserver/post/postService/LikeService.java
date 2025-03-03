package com.example.streamusserver.post.postService;

import com.example.streamusserver.post.dto.LikeResponse;

public interface LikeService {
    LikeResponse toggleLike(Long userId, Long postId);
    LikeResponse getLike(Long userId, Long postId);
    int getLikeCount(Long postId);
    boolean checkIfUserLikedPost(Long userId, Long postId);
}
