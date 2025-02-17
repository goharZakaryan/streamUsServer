package com.example.streamusserver.post.repository;

import com.example.streamusserver.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByAccountId(Long userProfileId);
    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<Post> findByIdLessThanOrderByCreatedAtDesc(Long id, Pageable pageable);
}

