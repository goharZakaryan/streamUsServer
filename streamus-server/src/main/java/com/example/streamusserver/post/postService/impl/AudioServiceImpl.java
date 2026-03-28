package com.example.streamusserver.post.postService.impl;

import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.dto.response.AudioItemResponseDto;
import com.example.streamusserver.post.dto.response.LikeResponse;
import com.example.streamusserver.post.mapper.AudioItemMapper;
import com.example.streamusserver.post.model.Audio;
import com.example.streamusserver.post.model.UserAudio;
import com.example.streamusserver.post.postService.AudioService;
import com.example.streamusserver.post.repository.AudioRepository;
import com.example.streamusserver.post.repository.UserAudioRepository;
import com.example.streamusserver.service.UserProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AudioServiceImpl implements AudioService {
    private final AudioRepository audioRepository;
    private final AudioItemMapper audioItemMapper;
    private final UserProfileService userProfileService;
    private final UserAudioRepository userAudioRepository;

    @Override
    public List<AudioItemResponseDto> getUserAudios(Long id) {
        List<Audio> allByUserProfileId = audioRepository.findAllByUserProfileId(id);
        List<Audio> favoriteAudios=getFavoriteAudios(id);
        allByUserProfileId.addAll(favoriteAudios);
        return audioItemMapper.toDtoList(allByUserProfileId);
    }

    @Override
    public List<AudioItemResponseDto> getAudios() {
        return audioItemMapper.toDtoList(audioRepository.findAll());
    }

    @Transactional
    public LikeResponse likeAudio(Long userId, Long audioId) {
        UserProfile user = userProfileService.findById(userId).orElseThrow();
        Audio audio = audioRepository.findById(audioId).orElseThrow();

        Optional<UserAudio> existing = userAudioRepository.findByUserAndAudio(user, audio);

        if (existing.isPresent()) {
            existing.get().setFavorite(false);
            userAudioRepository.save(existing.get());
            return new LikeResponse(false, 0);
        } else {
            UserAudio ua = new UserAudio();
            ua.setUser(user);
            ua.setAudio(audio);
            ua.setFavorite(true);
            userAudioRepository.save(ua);
            return new LikeResponse(true, 0);
        }
    }
    public List<Audio> getFavoriteAudios(Long userId) {

        UserProfile user = userProfileService.findById(userId).orElseThrow();

        return userAudioRepository.findByUserAndFavoriteTrue(user)
                .stream()
                .map(UserAudio::getAudio)
                .toList();
    }
}
