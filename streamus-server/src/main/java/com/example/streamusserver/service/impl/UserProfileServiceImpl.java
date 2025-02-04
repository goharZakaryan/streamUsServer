package com.example.streamusserver.service.impl;

import com.example.streamusserver.config.EmailConfig;
import com.example.streamusserver.dto.*;
import com.example.streamusserver.mapper.UserProfileMapper;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.repository.UserProfileRepository;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.FollowRequestService;
import com.example.streamusserver.service.UserProfileService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final EmailConfig emailConfig;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileMapper userProfileMapper;
    @Autowired
    @Lazy
    private  FollowRequestService followRequestService;

    @Value("${file.path}")
    private String sourceDir;

    /**
     * Retrieves the current user's profile.
     *
     * @return UserProfile of the current user or null if no user is authenticated
     */
    @Override
    public UserProfile getUserProfile() {
        // TODO: Implement actual user profile retrieval logic
        // Typically, this would involve getting the current authenticated user from SecurityContextHolder
        return null;
    }

    @Override
    public UserProfile editProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    /**
     * Save a new user profile with image upload.
     *
     * @param profileRequestDto User profile details
     * @return ProfileResponseDto with saved user details
     */
    @Override
    public ProfileResponseDto save(ProfileRequestDto profileRequestDto) {
        // Validate input
        if (profileRequestDto == null) {
            throw new IllegalArgumentException("Profile request cannot be null");
        }

        // Check if username already exists
        if (userProfileRepository.findByUsername(profileRequestDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }


        // Encode password before saving
        profileRequestDto.setPassword(passwordEncoder.encode(profileRequestDto.getPassword()));

        // Create and save user profile
        UserProfile userProfile = userProfileMapper.toEntity(profileRequestDto);
        UserProfile savedProfile = userProfileRepository.save(userProfile);
        String token = jwtUtil.generateToken(userProfile.getUsername());

        // Map to response DTO
        return mapToDto(savedProfile, false, -1, token);
    }

    /**
     * Authenticate user and generate JWT token.
     *
     * @param loginRequestDto Login credentials
     * @return ProfileResponseDto with user details and token
     */
    @Override
    public ProfileResponseDto signIn(LoginRequestDto loginRequestDto) {
        // Validate input
        if (loginRequestDto == null) {
            throw new IllegalArgumentException("Login request cannot be null");
        }

        // Find user by username
        UserProfile userProfile = userProfileRepository.findByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        // Verify password
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), userProfile.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        // Generate JWT token
        String token = jwtUtil.generateToken(userProfile.getUsername());

        // Build response
        ProfileResponseDto.AccountDetails accountDetails = new ProfileResponseDto.AccountDetails();
        accountDetails.setId(userProfile.getId());
        accountDetails.setUsername(userProfile.getUsername());
        accountDetails.setFullname(userProfile.getFullname());
        accountDetails.setEmail(userProfile.getEmail());

        return new ProfileResponseDto(false, 200, token, Collections.singletonList(accountDetails), userProfile.getId());
    }

    /**
     * Find user by username.
     *
     * @param username Username to search for
     * @return Optional of UserProfile
     */
    @Override
    public Optional<UserProfile> findByUsername(String username) {
        return userProfileRepository.findByUsername(username);
    }

    /**
     * Retrieve user profile by ID.
     *
     * @param id User profile ID
     * @return ProfileDto with user details
     */
    @Override
    public ProfileDto getProfile(Long id) {
        return userProfileRepository.findById(id)
                .map(userProfile -> {
                    userProfile.setFollowingsCount(followRequestService.countByFollowing(userProfile));
                    userProfile.setFollowersCount(followRequestService.countByFollower(userProfile));
                    return userProfileMapper.toProfileDto(userProfile);
                })
                .orElseThrow(() -> new NoSuchElementException("User profile not found"));
    }


    @Override
    public ProfileResponseDto authorize(AuthenticationRequestDto requestDto) {
        String username = jwtUtil.extractUsername(requestDto.getAccessToken());

        // Find user by username
        UserProfile userProfile = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        // Validate client ID
        if (!userProfile.getId().equals(requestDto.getAccountId())) {
            return new ProfileResponseDto(true, 403, "Invalid client ID", null, null);
        }

        // Validate JWT token
        if (!jwtUtil.isTokenValid(requestDto.getAccessToken())) {
            return new ProfileResponseDto(true, 401, "Invalid or expired token", null, null);
        }

        // Extract claims from the token

        // Optionally update FCM token
//        if (requestDto.getFcmRegId() != null && !requestDto.getFcmRegId().isEmpty()) {
//            userProfile.setFcmRegId(requestDto.getFcmRegId());
//            userProfileRepository.save(userProfile);
//        }

        // Build response
        ProfileResponseDto.AccountDetails accountDetails = new ProfileResponseDto.AccountDetails();
        accountDetails.setId(userProfile.getId());
        accountDetails.setUsername(userProfile.getUsername());
        accountDetails.setFullname(userProfile.getFullname());
        accountDetails.setEmail(userProfile.getEmail());
        accountDetails.setFollowersCount(followRequestService.countByFollower(userProfile));
        accountDetails.setFollowingsCount(followRequestService.countByFollowing(userProfile));

        return new ProfileResponseDto(false, 200, "Authorization successful", Collections.singletonList(accountDetails), userProfile.getId());
    }

//    public validateToken() {
//        if (!jwtUtil.isTokenValid(requestDto.getAccessToken())) {
//            return new ProfileResponseDto(true, 401, "Invalid or expired token", null, null);
//        }
//    }

    /**
     * Save uploaded profile image.
     *
     * @param file Uploaded image file
     * @return Image file name or empty string if no file
     */
    public String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return "";
        }

        try {
            // Ensure directory exists
            Path uploadPath = Paths.get(sourceDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            // Generate unique filename
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path targetLocation = uploadPath.resolve(fileName);

            // Copy file to target location
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + file.getOriginalFilename(), ex);
        }
    }

    /**
     * Map UserProfile to ProfileResponseDto.
     *
     * @param userProfile Source UserProfile
     * @param error       Error flag
     * @param errorCode   Error code
     * @param accessToken Access token
     * @return Mapped ProfileResponseDto
     */
    private ProfileResponseDto mapToDto(UserProfile userProfile, boolean error, int errorCode, String accessToken) {
        List<ProfileResponseDto.AccountDetails> accountDetailsList = new ArrayList<>();
        ProfileResponseDto.AccountDetails accountDetails = new ProfileResponseDto.AccountDetails();

        accountDetails.setId(userProfile.getId());
        accountDetails.setUsername(userProfile.getUsername());
        accountDetails.setFullname(userProfile.getFullname());
        accountDetails.setEmail(userProfile.getEmail());

        accountDetailsList.add(accountDetails);

        ProfileResponseDto dto = new ProfileResponseDto();
        dto.setAccessToken(accessToken);
        dto.setAccount(accountDetailsList);

        return dto;
    }

    /**
     * Send email confirmation with random verification code.
     *
     * @param email Recipient email address
     * @return Generated verification code
     */
    @Override
    public int confirmEmail(String email) {
        // Validate email
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        // Generate registration ID and verification code
        String registrationId = UUID.randomUUID().toString();
        Random random = new Random();
        int verificationCode = random.nextInt(900000) + 100000; // 6-digit code

        // Prepare and send email
        SendEmailRequestDto emailRequest = SendEmailRequestDto.builder()
                .subject("Email Verification")
                .body("Your verification code is: " + verificationCode)
                .receiver(email)
                .build();

        emailConfig.sendEmail(emailRequest);

        return verificationCode;
    }

    @Override
    public SearchResponse searchProfiles(SearchRequest searchRequest) {
        List<UserProfile> profiles = userProfileRepository.findAll();
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setItemCount(profiles.size());
        searchResponse.setItems(profiles);
        return searchResponse;
    }

    @Override
    public SearchResponse searchProfilesPreload(SearchRequest searchRequest) {
        List<UserProfile> profiles = userProfileRepository.findAll();
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setItemCount(profiles.size());
        searchResponse.setItems(profiles);
        return searchResponse;
    }

    public Optional<UserProfile> findById(Long id) {
        return userProfileRepository.findById(id);
    }
}