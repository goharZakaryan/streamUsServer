package com.example.streamusserver.service;

import com.example.streamusserver.dto.*;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.dto.response.UploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserProfileService {
    UserProfile getUserProfile();
    UserProfile editProfile(UserProfile userProfile);
    UploadResponseDto uploadProfilePic(MultipartFile file, Long accountId, String accessToken);
    ProfileResponseDto save(ProfileRequestDto userProfile);
    List<MediaItemDTO> getUserMedia(Long userId, int page, int size, String mediaType);
    SearchResponse searchProfiles(SearchRequest searchRequest);
    SearchResponse searchProfilesPreload(SearchRequest searchRequest);

    ProfileResponseDto signIn(LoginRequestDto userProfile);

    int confirmEmail(String confirmEmail);

    Optional<UserProfile> findByUsername(String username);
    public Optional<UserProfile> findById(Long id);
    ProfileDto getProfile(Long id);

    ProfileResponseDto authorize(AuthenticationRequestDto authenticationRequestDto);
}
