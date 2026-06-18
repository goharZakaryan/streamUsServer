package com.example.streamusserver.post.repository;

import com.example.streamusserver.post.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);
    boolean existsByUserIdAndComment_Id(Long userId, Long commentId);
    boolean existsByUserIdAndStory_Id(Long userId, Long storyId);
    void deleteByUserIdAndPostId(Long userId, Long postId);
    void deleteByUserIdAndCommentId(Long userId, Long commentId);
    void deleteByUserIdAndStory_Id(Long userId, Long storyId);
    int countByPostId(Long postId);
    int countByStory_Id(Long storyId);

    int countByComment_Id(Long commentId);
}
