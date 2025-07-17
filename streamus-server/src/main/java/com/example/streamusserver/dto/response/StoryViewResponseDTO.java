package com.example.streamusserver.dto.response;

import com.example.streamusserver.dto.ProfileDto;
import com.example.streamusserver.model.StoryView;
import com.example.streamusserver.model.UserProfile;

import java.time.LocalDateTime;

public class StoryViewResponseDTO {
    private Long id;
    private ProfileDto viewer;
    private LocalDateTime viewedAt;

    public StoryViewResponseDTO() {}

    public StoryViewResponseDTO(StoryView storyView) {
        this.id = storyView.getId();
        this.viewer = new ProfileDto(storyView.getViewer());
        this.viewedAt = storyView.getViewedAt();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ProfileDto getViewer() { return viewer; }
    public void setViewer(ProfileDto viewer) { this.viewer = viewer; }

    public LocalDateTime getViewedAt() { return viewedAt; }
    public void setViewedAt(LocalDateTime viewedAt) { this.viewedAt = viewedAt; }

}
