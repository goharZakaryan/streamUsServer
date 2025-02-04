package com.example.streamusserver.dto;

public class LoginRequestDto {
    private String username;
    private String password;
    private String CLIENT_ID;
    private String hash;
    private String appType;
    private String fcm_regId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public void setCLIENT_ID(String CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getFcm_regId() {
        return fcm_regId;
    }

    public void setFcm_regId(String fcm_regId) {
        this.fcm_regId = fcm_regId;
    }
}
