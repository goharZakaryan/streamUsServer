package com.example.streamusserver.service;

import com.example.streamusserver.model.Story;
import com.example.streamusserver.model.StoryGroup;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StoryService {

    void deleteStory(Long storyId, Long userId);

    List<Long> getStoryViewers(Long storyId, Long ownerId);

    void viewStory(Long storyId, Long viewerId);

    Story findById(Long storyId);

    Story createStory(Long userId, String storyType, MultipartFile mediaFile);

    List<StoryGroup> getStoriesForUser(Long userId, List<Long> followingIds);
}
