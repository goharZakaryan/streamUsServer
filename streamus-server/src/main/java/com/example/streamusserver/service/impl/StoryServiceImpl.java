package com.example.streamusserver.service.impl;

import com.example.streamusserver.dto.reduest.StoryRequestDto;
import com.example.streamusserver.model.Story;
import com.example.streamusserver.model.StoryGroup;
import com.example.streamusserver.post.model.enums.ImageType;
import com.example.streamusserver.repository.StoryRepository;
import com.example.streamusserver.service.StoryService;
import com.example.streamusserver.util.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private FileStorageService fileStorageService;

    public Story createStory(Long userId, String storyType, MultipartFile mediaFile) {
        try {
            // Upload media file
            String mediaUrl = fileStorageService.uploadFile(mediaFile, userId);

            // Determine media type
            ImageType mediaType = storyType.equalsIgnoreCase("video") ?
                    ImageType.VIDEO : ImageType.PHOTO;

            Story story = new Story(userId, "public/"+mediaUrl, mediaType);
            return storyRepository.save(story);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create story", e);
        }
    }

    public List<StoryGroup> getStoriesForUser(Long currentUserId, List<Long> followingUserIds) {
        // Clean up expired stories first
        storyRepository.deactivateExpiredStories(LocalDateTime.now());

        // Get all user IDs including current user
        List<Long> allUserIds = new ArrayList<>(followingUserIds);
        allUserIds.add(currentUserId);

        List<Story> stories = storyRepository.findActiveStoriesByUserIds(allUserIds, LocalDateTime.now());

        // Group stories by user
        Map<Long, List<Story>> groupedStories = stories.stream()
                .collect(Collectors.groupingBy(Story::getUserId));

        return groupedStories.entrySet().stream()
                .map(entry -> new StoryGroup(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> {
                    // Current user's stories first, then by latest story
                    if (a.getUserId().equals(currentUserId)) return -1;
                    if (b.getUserId().equals(currentUserId)) return 1;
                    return b.getLatestStoryTime().compareTo(a.getLatestStoryTime());
                })
                .collect(Collectors.toList());
    }

    public void viewStory(Long storyId, Long viewerId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));

        story.getViewedBy().add(viewerId);
        storyRepository.save(story);
    }

    public List<Long> getStoryViewers(Long storyId, Long ownerId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));

        if (!story.getUserId().equals(ownerId)) {
            throw new RuntimeException("Not authorized to view story viewers");
        }

        return new ArrayList<>(story.getViewedBy());
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
}
