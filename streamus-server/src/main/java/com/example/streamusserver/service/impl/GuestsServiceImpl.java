package com.example.streamusserver.service.impl;

import com.example.streamusserver.dto.guests.GuestDto;
import com.example.streamusserver.dto.guests.GuestSaveRequestDto;
import com.example.streamusserver.dto.guests.GuestsRequest;
import com.example.streamusserver.dto.guests.GuestsResponse;
import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.model.Guest;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.repository.GuestRepository;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.GuestsService;
import com.example.streamusserver.service.UserProfileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuestsServiceImpl implements GuestsService {
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public GuestsResponse getGuests(GuestsRequest request) {
        GuestsResponse response = new GuestsResponse();

        try {
            // Validate access token
            if (!jwtUtil.isTokenValid(request.getAccessToken())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to follow/unfollow on behalf of this account");
            }
            UserProfile guestToProfile = userProfileService.findById(request.getAccountId())
                    .orElseThrow(() -> new UserNotFoundException("Profile not found"));

            // Fetch guests
            List<Guest> guests = guestRepository.findByGuestTo(guestToProfile);

            response.setError(false);
            response.setItemId(guests.isEmpty() ? 0 : (int) guests.get(guests.size() - 1).getId());
            response.setGuests(guestsDto(guests));
            guests.forEach(guest -> guest.setViewed(true));
            guestRepository.saveAll(guests);
            return response;
        } catch (Exception e) {
            response.setError(true);
            return response;
        }
    }

    @Override
    public Boolean isGuestExist(Long request) {
        UserProfile guestToProfile = userProfileService.findById(request)
                .orElseThrow(() -> new UserNotFoundException("Profile not found"));
        return guestRepository.existsByGuestToAndViewed(guestToProfile, false);
    }

    @Override
    @Transactional
    public Guest saveGuest(GuestSaveRequestDto request) {
        // Validate access token and profiles
        UserProfile guestToProfile = userProfileService.findById(request.getGuestTo())
                .orElseThrow(() -> new UserNotFoundException("Profile not found"));

        UserProfile guestUserProfile = userProfileService.findById(request.getGuestUserId())
                .orElseThrow(() -> new UserNotFoundException("Guest profile not found"));

        // Check if guest entry already exists
        Optional<Guest> existingGuest = guestRepository.findByGuestToAndGuestUserId(
                guestToProfile,
                guestUserProfile
        );

        if (existingGuest.isPresent()) {
            Guest guest = existingGuest.get();
            guest.setViewed(false);
            guestRepository.save(guest);
            return guest;
        }

        // Create new guest entry
        Guest guest = new Guest();
        guest.setGuestTo(guestToProfile);
        guest.setGuestUserId(guestUserProfile);
        guest.setGuestUserUsername(guestUserProfile.getUsername());
        guest.setGuestUserFullname(guestUserProfile.getFullname());
        guest.setViewed(false);
        guest.setTimeAgo(LocalDateTime.now().toString());
        guest.setOnline(true);

        return guestRepository.save(guest);
    }

    private List<GuestDto> guestsDto(List<Guest> guests) {
        return guests.stream()
                .map(guest -> {
                    GuestDto guestDto = new GuestDto();
                    guestDto.setId(guest.getId());
                    guestDto.setViewed(guest.getViewed());
                    guestDto.setGuestUserFullname(guest.getGuestUserFullname());
                    guestDto.setGuestUserUsername(guest.getGuestUserUsername());
                    guestDto.setGuestUserPhoto(guest.getGuestUserPhoto());
                    guestDto.setGuestTo(guest.getGuestTo().getId()); // Assuming guestTo is a UserProfile
                    guestDto.setGuestUserId(guest.getGuestUserId().getId()); // Assuming guestUserId is a UserProfile
                    guestDto.setVerify(guest.getVerify());
                    guestDto.setVip(guest.getVip());
                    guestDto.setTimeAgo(guest.getTimeAgo());

                    return guestDto;
                })
                .collect(Collectors.toList());
    }


}
