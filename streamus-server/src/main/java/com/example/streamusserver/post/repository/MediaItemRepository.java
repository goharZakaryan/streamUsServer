package com.example.streamusserver.post.repository;

import com.example.streamusserver.post.model.MediaItem;
import com.example.streamusserver.post.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaItemRepository extends JpaRepository<MediaItem, Long> {
}
