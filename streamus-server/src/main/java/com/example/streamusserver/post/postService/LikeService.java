package com.example.streamusserver.post.postService;

import com.example.streamusserver.post.dto.response.LikeResponse;

public interface LikeService {
    LikeResponse toggleLike(Long userId, Long postId);
    LikeResponse likeStory(Long userId, Long storyId);
    LikeResponse getLike(Long userId, Long postId);
    int getLikeCount(Long postId);
    LikeResponse checkIfUserLikedStory(Long userId, Long storyId);
    boolean checkIfUserLikedPost(Long userId, Long postId);
}
