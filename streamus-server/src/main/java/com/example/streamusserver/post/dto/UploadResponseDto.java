package com.example.streamusserver.post.dto;

public class UploadResponseDto {
    private boolean error;
    private String message;
    private String imgUrl;

    public UploadResponseDto(boolean error, String message, String imgUrl) {
        this.error = error;
        this.message = message;
        this.imgUrl = imgUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
