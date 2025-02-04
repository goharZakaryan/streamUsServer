package com.example.streamusserver.dto;

import java.util.List;

public class ProfileResponseDto {
    private boolean error;
    private int errorCode;
    private long accountId;
    private String accessToken; // Add a message field for error responses
    private List<AccountDetails> account;

    public ProfileResponseDto() {
    }

    public ProfileResponseDto(boolean error, int errorCode, String accessToken, List<AccountDetails> account,Long id) {
        this.error = error;
        this.errorCode = errorCode;
        this.accessToken = accessToken;
        this.account = account;
        this.accountId = id;
    }

    // Getters and Setters
    public boolean isError() {
        return error;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken()  {
        this.accessToken = accessToken;
    }

    public List<AccountDetails> getAccount() {
        return account;
    }

    public void setAccount(List<AccountDetails> account) {
        this.account = account;
    }

    public static class AccountDetails {
        private Long id;
        private String username;
        private String fullname;
        private String email;
        private int followingsCount;
        private  int followersCount;

        public int getFollowingsCount() {
            return followingsCount;
        }

        public void setFollowingsCount(int followingsCount) {
            this.followingsCount = followingsCount;
        }

        public int getFollowersCount() {
            return followersCount;
        }

        public void setFollowersCount(int followersCount) {
            this.followersCount = followersCount;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
