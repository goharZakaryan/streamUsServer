package com.example.streamusserver.post.dto.request;

public class HideItemRequestDto {
    private Long userId;
    private Long postId;
    private String accessToken;

    public HideItemRequestDto(Long userId, Long postId, String accessToken) {
        this.userId = userId;
        this.postId = postId;
        this.accessToken = accessToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
