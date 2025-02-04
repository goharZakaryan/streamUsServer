package com.example.streamusserver.repository;

import com.example.streamusserver.model.UserProfile;
import org.apache.catalina.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsernameAndPassword(String usernameA, String passwordA);

    List<UserProfile> findAll();
    Optional<UserProfile> findByUsername(String username);
}
