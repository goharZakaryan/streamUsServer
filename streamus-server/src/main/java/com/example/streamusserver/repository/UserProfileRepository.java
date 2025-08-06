package com.example.streamusserver.repository;

import com.example.streamusserver.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsernameAndPassword(String usernameA, String passwordA);

    @Query("SELECT p.friends FROM UserProfile p WHERE p.id = :profileId")
    Set<UserProfile> findFriendsByProfileId(@Param("profileId") Long profileId);

    List<UserProfile> findAll();

    Optional<UserProfile> findByUsername(String username);
}
