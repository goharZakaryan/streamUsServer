package com.example.streamusserver.post.repository;

import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.model.Audio;
import com.example.streamusserver.post.model.UserAudio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAudioRepository extends JpaRepository<UserAudio, Long> {
    Optional<UserAudio> findByUserAndAudio(UserProfile userProfile, Audio audio);
    List<UserAudio> findByUserAndFavoriteTrue(UserProfile user);

}
