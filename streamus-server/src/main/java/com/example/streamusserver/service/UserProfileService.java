package com.example.streamusserver.service;

import com.example.streamusserver.dto.*;
import com.example.streamusserver.model.UserProfile;

import java.util.Optional;

public interface UserProfileService {
    UserProfile getUserProfile();
    UserProfile editProfile(UserProfile userProfile);

    ProfileResponseDto save(ProfileRequestDto userProfile);

    SearchResponse searchProfiles(SearchRequest searchRequest);
    SearchResponse searchProfilesPreload(SearchRequest searchRequest);

    ProfileResponseDto signIn(LoginRequestDto userProfile);

    int confirmEmail(String confirmEmail);

    Optional<UserProfile> findByUsername(String username);
    public Optional<UserProfile> findById(Long id);
    ProfileDto getProfile(Long id);

    ProfileResponseDto authorize(AuthenticationRequestDto authenticationRequestDto);
}
