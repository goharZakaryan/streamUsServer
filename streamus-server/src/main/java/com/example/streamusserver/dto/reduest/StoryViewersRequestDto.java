package com.example.streamusserver.dto.reduest;

public class StoryViewersRequestDto {
    private long storyId;
    private long accountId;

    private String accessToken;

    private long itemId;

    private String language;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getLanguage() {
        return language;
    }

    public long getStoryId() {
        return storyId;
    }

    public void setStoryId(long storyId) {
        this.storyId = storyId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
