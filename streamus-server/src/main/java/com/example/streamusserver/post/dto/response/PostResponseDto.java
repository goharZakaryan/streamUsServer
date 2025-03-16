package com.example.streamusserver.post.dto.response;

import java.util.Map;

public class PostResponseDto {
    private boolean error;
    private String message;
    private long postId;
    private Map<String, String> images;
    private String videoUrl;

    public PostResponseDto() {
    }

    public PostResponseDto(boolean error, String message, long postId, Map<String, String> images, String videoUrl) {
        this.error = error;
        this.message = message;
        this.postId = postId;
        this.images = images;
        this.videoUrl = videoUrl;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
// Getters and Setters
}
