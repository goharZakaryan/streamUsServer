package com.example.streamusserver.service.impl;

import com.example.streamusserver.dto.guests.GuestDto;
import com.example.streamusserver.dto.guests.GuestsRequest;
import com.example.streamusserver.dto.guests.GuestsResponse;
import com.example.streamusserver.dto.reduest.StoryViewersRequestDto;
import com.example.streamusserver.dto.response.StoryViewResponseDTO;
import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.model.*;
import com.example.streamusserver.post.model.enums.ImageType;
import com.example.streamusserver.repository.StoryRepository;
import com.example.streamusserver.repository.StoryViewRepository;
import com.example.streamusserver.service.StoryService;
import com.example.streamusserver.service.UserProfileService;
import com.example.streamusserver.util.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class StoryServiceImpl implements StoryService {
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private StoryViewRepository storyViewRepository;

    public Story createStory(Long userId, String storyType, MultipartFile mediaFile) {
        try {
            // Upload media file
            String mediaUrl = fileStorageService.uploadFile(mediaFile, userId);

            // Determine media type
            ImageType mediaType = storyType.equalsIgnoreCase("video") ?
                    ImageType.VIDEO : ImageType.PHOTO;

            Story story = new Story(userId, "public/" + mediaUrl, mediaType);
            return storyRepository.save(story);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create story", e);
        }
    }
    public List<StoryGroup> getStoriesForUser(Long currentUserId, List<Long> followingUserIds) {
        // Deactivate expired stories
        storyRepository.deactivateExpiredStories(LocalDateTime.now());

        // Get all user IDs (current + following)
        List<Long> allUserIds = new ArrayList<>(followingUserIds);
        allUserIds.add(currentUserId);

        // Fetch active stories
        List<Story> stories = storyRepository.findActiveStoriesByUserIds(allUserIds, LocalDateTime.now());

        // Get IDs of stories viewed by current user

        // Mark each story as viewed or not
        for (Story story : stories) {
            int viewersCount=storyViewRepository.findCountByStoryId(story.getId());
            if (viewersCount>0) {
                story.setViewed(true);
            }
            story.setViewed(false);

            story.setViewerCount(viewersCount);
        }

        // Group stories by user
        Map<Long, List<Story>> groupedStories = stories.stream()
                .collect(Collectors.groupingBy(Story::getUserId));

        // Convert to StoryGroup and sort
        return groupedStories.entrySet().stream()
                .map(entry -> new StoryGroup(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> {
                    if (a.getUserId().equals(currentUserId)) return -1;
                    if (b.getUserId().equals(currentUserId)) return 1;
                    return b.getLatestStoryTime().compareTo(a.getLatestStoryTime());
                })
                .collect(Collectors.toList());
    }

    @Override
    public GuestsResponse getViewers(StoryViewersRequestDto request) {
        GuestsResponse response = new GuestsResponse();

        try {
//            // Validate access token
//            if (!jwtUtil.isTokenValid(request.getAccessToken())) {
//                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to follow/unfollow on behalf of this account");
//            }
            UserProfile guestToProfile = userProfileService.findById(request.getAccountId())
                    .orElseThrow(() -> new UserNotFoundException("Profile not found"));

            // Fetch guests
            List<StoryView> guests = storyViewRepository.findByStoryIdOrderByViewedAtDesc(request.getStoryId());

            response.setError(false);
            response.setItemId(guests.isEmpty() ? 0 : (int) guests.get(guests.size() - 1).getId());
            response.setGuests(guestsDto(guests));
            guests.forEach(guest -> guest.setViewed(true));
            return response;
        } catch (Exception e) {
            response.setError(true);
            return response;
        }    }


    public void viewStory(Long storyId, Long viewerId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));

        story.getViewedBy().add(viewerId);
        storyRepository.save(story);
    }

    @Override
    public Story findById(Long storyId) {
        return storyRepository.findById(storyId).get();
    }

    public List<StoryViewResponseDTO> getStoryViewers(Long storyId, Long ownerId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));

        // Only story owner can see viewers
        if (!story.getUserId().equals(ownerId)) {
            throw new RuntimeException("Access denied");
        }

        List<StoryView> views = storyViewRepository.findByStoryIdOrderByViewedAtDesc(storyId);
        return views.stream()
                .map(StoryViewResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void deleteStory(Long userId, Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));

        if (!story.getUserId().equals(userId)) {
            throw new RuntimeException("Not authorized to delete this story");
        }

        story.setActive(false);
        storyRepository.save(story);
    }
    private List<GuestDto> guestsDto(List<StoryView> guests) {
        return guests.stream()
                .map(guest -> {
                    GuestDto guestDto = new GuestDto();
                    guestDto.setId(guest.getId());
                    guestDto.setViewed(guest.getViewed());
                    guestDto.setGuestUserFullname(guest.getGuestUserFullname());
                    guestDto.setGuestUserUsername(guest.getGuestUserUsername());
                    guestDto.setGuestUserPhoto(guest.getGuestUserPhoto());
//                    guestDto.setGuestTo(guest.getGuestTo().getId()); // Assuming guestTo is a UserProfile
//                    guestDto.setGuestUserId(guest.g().getId()); // Assuming guestUserId is a UserProfile
                    guestDto.setVerify(guest.getVerify());
                    guestDto.setVip(guest.getVip());
                    guestDto.setTimeAgo(guest.getTimeAgo());

                    return guestDto;
                })
                .collect(Collectors.toList());
    }

}
