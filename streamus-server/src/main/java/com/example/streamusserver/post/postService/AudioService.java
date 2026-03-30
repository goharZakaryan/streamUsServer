package com.example.streamusserver.post.postService;

import com.example.streamusserver.post.dto.response.AudioItemResponseDto;
import com.example.streamusserver.post.dto.response.LikeResponse;

import java.util.List;

public interface AudioService {
    List<AudioItemResponseDto> getUserAudios(Long id);

    List<AudioItemResponseDto> getAudios();

    LikeResponse likeAudio(Long userId, Long audioId);
    void deleteAudio(Long userId, Long audioId);
}
