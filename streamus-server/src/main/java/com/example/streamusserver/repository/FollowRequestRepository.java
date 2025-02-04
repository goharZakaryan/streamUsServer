package com.example.streamusserver.repository;

import com.example.streamusserver.model.Follower;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.model.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRequestRepository extends JpaRepository<Follower, Long> {
    boolean existsByFollowerAndFollowing(UserProfile follower, UserProfile following);
    void deleteByFollowerAndFollowing(UserProfile follower, UserProfile following);
    int countByFollowing(UserProfile following);
    int countByFollower(UserProfile following);
}
