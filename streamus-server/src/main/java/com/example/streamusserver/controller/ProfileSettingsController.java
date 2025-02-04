package com.example.streamusserver.controller;

import com.example.streamusserver.dto.ProfileSettingsRequestDto;
import com.example.streamusserver.dto.ProfileSettingsResponseDto;
import com.example.streamusserver.service.ProfileSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class ProfileSettingsController {
    @Autowired
    private ProfileSettingsService profileSettingsService;

    @PostMapping("/save-settings")
    public ResponseEntity<ProfileSettingsResponseDto> saveProfileSettings(@RequestBody ProfileSettingsRequestDto request) {
        System.out.println("jjjj");
        ProfileSettingsResponseDto response = profileSettingsService.updateProfileSettings(request);
        if (response.isError()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
}
