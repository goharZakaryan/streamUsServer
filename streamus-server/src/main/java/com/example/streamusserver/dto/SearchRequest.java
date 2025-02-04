package com.example.streamusserver.dto;

public class SearchRequest {
    private Long accountId;
    private String accessToken;
    private String query;
    private int userId;
    private int gender;
    private Boolean online;
    private Boolean photo;

    public Boolean getPhoto() {
        return photo;
    }

    public void setPhoto(Boolean photo) {
        this.photo = photo;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }


}
