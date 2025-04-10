package com.example.streamusserver.post.postService.impl;

import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.notification.service.NotificationService;
import com.example.streamusserver.post.dto.response.LikeResponse;
import com.example.streamusserver.post.model.Like;
import com.example.streamusserver.post.model.Post;
import com.example.streamusserver.post.postService.LikeService;
import com.example.streamusserver.post.repository.LikeRepository;
import com.example.streamusserver.post.repository.PostRepository;
import com.example.streamusserver.service.UserProfileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private NotificationService notificationService;

    @Transactional
    public LikeResponse toggleLike(Long userId, Long postId) {
        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(userId, postId);
        int likeCount = getLikeCount(postId);
        if (alreadyLiked) {
            likeRepository.deleteByUserIdAndPostId(userId, postId);
            return new LikeResponse(false, --likeCount); // Post unliked
        } else {
            UserProfile user = userProfileService.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));

            Like like = new Like();
            like.setUser(user);
            like.setPost(post);
            like.setCreatedAt(LocalDateTime.now());

            likeRepository.save(like);
            if (!userId.equals(post.getAccount().getId())){
                notificationService.createLikeNotification(userId,postId,user,post);

            }
            return new LikeResponse(true, ++likeCount); // Post liked
        }
    }

    public LikeResponse getLike(Long userId, Long postId) {
        boolean alreadyLiked = likeRepository.existsByUserIdAndPostId(userId, postId);

        return new LikeResponse(alreadyLiked, getLikeCount(postId)); // Post liked

    }

    public boolean checkIfUserLikedPost(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }

    public int getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}
