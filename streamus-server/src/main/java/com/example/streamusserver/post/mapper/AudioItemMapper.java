package com.example.streamusserver.post.mapper;

import com.example.streamusserver.post.dto.response.AudioItemResponseDto;
import com.example.streamusserver.post.model.Audio;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AudioItemMapper {

    AudioItemResponseDto toDto(Audio audioItem);

    default List<AudioItemResponseDto> toDtoList(List<Audio> audioList) {
        if (audioList == null) return null;

        return audioList.stream()
                .map(this::toDto)
                .toList();
    }
    List<Audio> toEntityList(List<AudioItemResponseDto> dtos);
}
