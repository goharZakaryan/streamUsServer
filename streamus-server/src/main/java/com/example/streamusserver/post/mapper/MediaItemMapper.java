package com.example.streamusserver.post.mapper;

import com.example.streamusserver.dto.MediaItemDTO;
import com.example.streamusserver.post.dto.response.MediaItemResponseDto;
import com.example.streamusserver.post.model.MediaItem;
import com.example.streamusserver.post.model.enums.ImageType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MediaItemMapper {
    public MediaItemDTO convertToMediaItemDTO(MediaItem mediaItem) {
        MediaItemDTO dto = new MediaItemDTO();
        dto.setId(mediaItem.getId());
        dto.setType(mediaItem.getType().name());

        if (mediaItem.getType().name().equals(ImageType.PHOTO.name())) { // Image
            dto.setUrl(mediaItem.getImageUrl());
            dto.setFileName(mediaItem.getSelectedImageFileName());
        } else if (mediaItem.getType().name().equals(ImageType.VIDEO.name())) { // Video
            dto.setUrl(mediaItem.getVideoUrl());
            dto.setFileName(mediaItem.getSelectedVideoFileName());
            dto.setThumbnailUrl(mediaItem.getVideoUrl()); // Set thumbnail if available
        }

        return dto;
    }

    public List<MediaItemResponseDto> convertToMediaItemDTO(List<MediaItem> mediaItems) {
        List<MediaItemResponseDto> mediaItemResponseDtos = new ArrayList<>();
        for (MediaItem mediaItem : mediaItems) {
            MediaItemResponseDto dto = new MediaItemResponseDto();
            dto.setId(mediaItem.getId());
            dto.setImageUrl(mediaItem.getImageUrl());
            dto.setVideoUrl(mediaItem.getVideoUrl());
            dto.setFileName(mediaItem.getSelectedImageFileName());
            dto.setThumbnailUrl(mediaItem.getVideoUrl());

            dto.setType(mediaItem.getType().name().equalsIgnoreCase(ImageType.PHOTO.name()) ? 1 : 0);
            mediaItemResponseDtos.add(dto);
        }
        return mediaItemResponseDtos;
    }
}
