package com.example.streamusserver.post.dto;

public class UploadResponseDto {
    private boolean error;
    private String message;
    private String imgUrl;
    private String videoUrl;




    public UploadResponseDto(boolean error, String message, String imgUrl) {
        this.error = error;
        this.message = message;
        this.imgUrl = imgUrl;

    }
    public UploadResponseDto(boolean error, String message, String imgUrl,String videoUrl) {
        this.error = error;
        this.message = message;
        this.imgUrl = imgUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
