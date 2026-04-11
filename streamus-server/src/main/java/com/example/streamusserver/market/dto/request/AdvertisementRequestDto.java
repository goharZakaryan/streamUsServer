package com.example.streamusserver.market.dto.request;

public class AdvertisementRequestDto {
    private String title;
    private String description;
//    private AdType type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
