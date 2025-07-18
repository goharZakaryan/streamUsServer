package com.example.streamusserver.dto.response;

import java.util.List;

public class StoryViewersResponseDTO {
    private int viewerCount;
    private List<UserDTO> viewers;

    public int getViewerCount() {
        return viewerCount;
    }

    public List<UserDTO> getViewers() {
        return viewers;
    }

    public static class UserDTO {
        private Long id;
        private String username;
        private String profileImageUrl;

        // getters
    }

    public void setViewerCount(int viewerCount) {
        this.viewerCount = viewerCount;
    }

    public void setViewers(List<UserDTO> viewers) {
        this.viewers = viewers;
    }
}
