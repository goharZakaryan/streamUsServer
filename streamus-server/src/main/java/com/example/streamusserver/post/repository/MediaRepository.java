package com.example.streamusserver.post.repository;

import com.example.streamusserver.post.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
