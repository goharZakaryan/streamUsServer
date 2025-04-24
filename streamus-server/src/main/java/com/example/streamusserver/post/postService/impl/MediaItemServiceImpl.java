package com.example.streamusserver.post.postService.impl;

import com.example.streamusserver.dto.MediaItemDTO;
import com.example.streamusserver.post.model.MediaItem;
import com.example.streamusserver.post.model.enums.ImageType;

public class MediaItemServiceImpl {


    private MediaItemDTO convertToMediaItemDTO(MediaItem mediaItem) {
        MediaItemDTO dto = new MediaItemDTO();
        dto.setId(mediaItem.getId());
        dto.setType(mediaItem.getType().name());

        if (mediaItem.getType().equals(ImageType.PHOTO)) { // Image
            dto.setUrl(mediaItem.getImageUrl());
            dto.setFileName(mediaItem.getSelectedImageFileName());
        } else if (mediaItem.getType().equals(ImageType.VIDEO)) { // Video
            dto.setUrl(mediaItem.getVideoUrl());
            dto.setFileName(mediaItem.getSelectedVideoFileName());
            dto.setThumbnailUrl(mediaItem.getVideoUrl()); // Set thumbnail if available
        }

        return dto;
    }
}
