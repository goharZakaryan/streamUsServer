package com.example.streamusserver.post.repository;

import com.example.streamusserver.post.model.MediaItem;
import com.example.streamusserver.post.model.PostImage;
import com.example.streamusserver.post.model.enums.ImageType;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaItemRepository extends JpaRepository<MediaItem, Long> {
//    Page findByPostAccountIdAndType(Long userId, String mediaType, Pageable pageable);

    List<MediaItem> findByPostAccountIdAndType(Long post_account_id, ImageType type);
}
