package com.example.streamusserver.controller;


import com.example.streamusserver.dto.*;
import com.example.streamusserver.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserProfileController {
    private final UserProfileService userProfileService;
//
//    @PostMapping("/account/save-settings")
//    public ResponseEntity<ProfileSettingsResponseDto> saveProfileSettings() {
//        System.out.println("jjjj");
//        ProfileSettingsResponseDto response = new ProfileSettingsResponseDto();
////        ProfileSettingsResponseDto response = profileSettingsService.updateProfileSettings(request);
////        if (response.isError()) {
////            return ResponseEntity.badRequest().body(response);
////        }
//
//        return ResponseEntity.ok(response);
//    }
    @PostMapping("/account/signUp")
    public ResponseEntity<ProfileResponseDto> signUp(@RequestBody ProfileRequestDto profileRequestDto) {

        return ResponseEntity.ok(userProfileService.save(profileRequestDto));
    }

    @PostMapping
    public ResponseEntity<SearchResponse> searchProfiles(@RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(userProfileService.searchProfiles(searchRequest));

    }

    @PostMapping("/search/preload")
    public ResponseEntity<SearchResponse> preloadProfiles(@RequestBody SearchRequest request) {
        // Validate access token (example, customize as needed)
//        if (!"valid_token".equals(request.getAccessToken())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new SearchResponse(true, 0, null));
//        }


        return ResponseEntity.ok(userProfileService.searchProfilesPreload(request));
    }

    @GetMapping("/account/signIn")
    public ResponseEntity<ProfileResponseDto> signIn(@RequestBody LoginRequestDto loginRequestDto) {
        System.out.println(loginRequestDto.getUsername());
        return ResponseEntity.ok(userProfileService.signIn(loginRequestDto));
    }

    @GetMapping("/account/getProfile")
    public ResponseEntity<ProfileDto> getProfile(@RequestParam("profileId") Long id) {

        return ResponseEntity.ok(userProfileService.getProfile(id));
    }

    @GetMapping("/account/confirmEmail")
    public ResponseEntity<Integer> confirmEmail(@RequestParam("confirmEmail") String confirmEmail) {
        try {
            Integer result = userProfileService.confirmEmail(confirmEmail);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            // Return a meaningful error code (e.g., HTTP 500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/account/signIn")
    public ResponseEntity<ProfileResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        return ResponseEntity.ok(userProfileService.signIn(loginRequestDto));
    }

    @PostMapping("/account/authorize")
    public ResponseEntity<ProfileResponseDto> authorize(@RequestBody AuthenticationRequestDto requestDto) {

        return ResponseEntity.ok(userProfileService.authorize(requestDto));
    }


}
