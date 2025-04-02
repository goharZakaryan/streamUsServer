package com.example.streamusserver.post.dto.request;


public class StreamRequestDto {


    private long accountId;


    private String accessToken;

    private int itemId;

    private String language;
    private int limit;
    public StreamRequestDto() {
    }
    public StreamRequestDto(long accountId, String accessToken, int itemId, String language) {
        this.accountId = accountId;
        this.accessToken = accessToken;
        this.itemId = itemId;
        this.language = language;
    }

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

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLimit() {
        return limit;
    }
}

