package com.example.streamusserver.service.impl;

import com.example.streamusserver.dto.ProfileSettingsRequestDto;
import com.example.streamusserver.dto.ProfileSettingsResponseDto;
import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.ProfileSettingsService;
import com.example.streamusserver.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileSettingsServiceImpl implements ProfileSettingsService {
   private final UserProfileService userProfileService;
    private final JwtUtil jwtUtil;

    @Override
    public ProfileSettingsResponseDto updateProfileSettings(ProfileSettingsRequestDto request) {
        ProfileSettingsResponseDto response = new ProfileSettingsResponseDto();

        try {
            // Validate access token (implement your logic)
            if (!jwtUtil.isTokenValid(request.getAccessToken())) {
                response.setError(true);
                return response;
            }

            UserProfile profile = userProfileService.findById(request.getAccountId())
                    .orElseThrow(() -> new UserNotFoundException("Profile not found"));

            // Update profile fields
            profile.setFullname(request.getFullname());
            profile.setLocation(request.getLocation());
            profile.setBio(request.getBio());
            profile.setSex(request.getSex());
            profile.setYear(request.getYear());
            profile.setMonth(request.getMonth());
            profile.setDay(request.getDay());

            // Save updated profile
            UserProfile savedProfile = userProfileService.editProfile(profile);

            // Populate response
            response.setError(false);
            response.setFullname(savedProfile.getFullname());
            response.setLocation(savedProfile.getLocation());

            response.setBio(savedProfile.getBio());

            return response;
        } catch (Exception e) {
            response.setError(true);
            return response;
        }
    }
}
