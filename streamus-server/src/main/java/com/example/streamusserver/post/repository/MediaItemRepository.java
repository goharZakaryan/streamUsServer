package com.example.streamusserver.post.repository;

import com.example.streamusserver.post.model.MediaItem;
import com.example.streamusserver.post.model.enums.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MediaItemRepository extends JpaRepository<MediaItem, Long> {

    //    Page findByPostAccountIdAndType(Long userId, String mediaType, Pageable pageable);
    void deleteByVideoUrl(String imageUrl);
    MediaItem findByImageUrl(String imageUrl);

    List<MediaItem> findByPostAccountIdAndType(Long post_account_id, ImageType type);
}
