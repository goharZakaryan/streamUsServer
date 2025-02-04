package com.example.streamusserver.post.repository;

import com.example.streamusserver.post.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPost_Id(Long postId);
}
