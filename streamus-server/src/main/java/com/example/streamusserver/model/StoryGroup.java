package com.example.streamusserver.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class StoryGroup implements Serializable {
    private Long userId;
    private String username;
    private String profilePicture;
    private List<Story> stories;
    private boolean hasUnviewedStories;

    public StoryGroup(Long userId, List<Story> stories) {
        this.userId = userId;
        this.stories = stories;
        this.hasUnviewedStories = stories.stream()
                .anyMatch(story -> !story.getViewedBy().contains(userId));
    }

    public LocalDateTime getLatestStoryTime() {
        return stories.stream()
                .map(Story::getCreatedAt)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.MIN);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public boolean isHasUnviewedStories() {
        return hasUnviewedStories;
    }

    public void setHasUnviewedStories(boolean hasUnviewedStories) {
        this.hasUnviewedStories = hasUnviewedStories;
    }
}
