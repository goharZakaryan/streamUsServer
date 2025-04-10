package com.example.streamusserver.post.repository;

import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.model.HidePost;
import com.example.streamusserver.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiddenPostRepository extends JpaRepository<HidePost,Long> {
    void deleteByUserAndPost(UserProfile user, Post post);
}
