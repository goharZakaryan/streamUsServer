package com.example.streamusserver.service;

import com.example.streamusserver.dto.FollowRequestDto;
import com.example.streamusserver.dto.FollowResponseDto;
import com.example.streamusserver.model.UserProfile;

public interface FollowRequestService {

    boolean isFollowing(Long authenticatedUser, Long profile);
    int countByFollowing(UserProfile userProfile);
    int countByFollower(UserProfile userProfile);
    FollowResponseDto handleFollow(FollowRequestDto request);
}
