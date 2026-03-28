package com.example.streamusserver.post.repository;

import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.model.Audio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AudioRepository extends JpaRepository<Audio, Long> {
    List<Audio> findAllByUserProfileId(Long userId);
}
