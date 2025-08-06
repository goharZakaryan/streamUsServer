package com.example.streamusserver.service.impl;

import com.example.streamusserver.dto.FollowRequestDto;
import com.example.streamusserver.dto.FollowResponseDto;
import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.model.Follower;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.repository.FollowRequestRepository;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.FollowRequestService;
import com.example.streamusserver.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FollowRequestServiceImpl implements FollowRequestService {

    private final UserProfileService userRepository;
    private final FollowRequestRepository followRequestRepository;

    private final JwtUtil jwtUtil;

    public FollowRequestServiceImpl(UserProfileService userRepository, JwtUtil jwtUtil, FollowRequestRepository friendRequestRepository) {
        this.userRepository = userRepository;
        this.followRequestRepository = friendRequestRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public FollowResponseDto handleFollow(FollowRequestDto request) {
        // Extract authenticated user from SecurityContext
        String authenticatedUsername = jwtUtil.extractUsername(request.getAccessToken());

        // Find the authenticated user's profile
        UserProfile authenticatedUser = userRepository.findByUsername(authenticatedUsername)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));

        if (!jwtUtil.isTokenValid(request.getAccessToken())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to follow/unfollow on behalf of this account");
        }
        try {
            UserProfile profile = userRepository.findById(request.getProfileId())
                    .orElseThrow(() -> new UserNotFoundException("Profile not found"));

            boolean isFollowing = followRequestRepository.existsByFollowerAndFollowing(authenticatedUser, profile);

            if (isFollowing) {
                // Unfollow
                followRequestRepository.deleteByFollowerAndFollowing(authenticatedUser, profile);
            } else {
                // Follow
                Follower newFollower = Follower.builder()
                        .follower(authenticatedUser)
                        .following(profile)
                        .createdAt(LocalDateTime.now())
                        .build();
                followRequestRepository.save(newFollower);
            }

            // Get updated followers count
            int followersCount = followRequestRepository.countByFollowing(profile);

            return FollowResponseDto.builder()
                    .error(false)
                    .follow(!isFollowing)
                    .followersCount(followersCount)
                    .build();

        } catch (Exception e) {
            log.error("Error in follow/unfollow process", e);
            return FollowResponseDto.builder()
                    .error(true)
                    .build();
        }
    }

    @Override
    public List<UserProfile> findAllFollowers(Long profile) {
        return followRequestRepository.findAllFollowerByFollowingId(profile);
    }

    public boolean isFollowing(Long authenticatedUser, Long profile) {
        UserProfile authUser = userRepository.findById(authenticatedUser)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));
        UserProfile profile1 = userRepository.findById(profile)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));
        return followRequestRepository.existsByFollowerAndFollowing(authUser, profile1);
    }

    @Override
    public int countByFollower(UserProfile userProfile) {
        return followRequestRepository.countByFollower(userProfile);
    }

    @Override
    public int countByFollowing(UserProfile userProfile) {
        return followRequestRepository.countByFollowing(userProfile);
    }
}
