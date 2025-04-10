package com.example.streamusserver.post.repository;

import com.example.streamusserver.post.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAccountId(Long userProfileId);
    @Query("SELECT p FROM Post p WHERE p.id NOT IN " +
            "(SELECT h.post.id FROM HidePost h WHERE h.user.id = :userId) " +
            "ORDER BY p.createdAt DESC")
    List<Post> findAllByOrderByCreatedAtDesc(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.id NOT IN " +
            "(SELECT h.post.id FROM HidePost h WHERE h.user.id = :userId) " +
            "ORDER BY p.createdAt DESC")
    List<Post> findByIdLessThanOrderByCreatedAtDesc(Long id, Pageable pageable);
}

