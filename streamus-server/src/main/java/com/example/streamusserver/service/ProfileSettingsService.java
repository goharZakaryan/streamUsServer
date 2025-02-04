package com.example.streamusserver.service;

import com.example.streamusserver.dto.ProfileSettingsRequestDto;
import com.example.streamusserver.dto.ProfileSettingsResponseDto;

public interface ProfileSettingsService {
    ProfileSettingsResponseDto updateProfileSettings(ProfileSettingsRequestDto request);
}
