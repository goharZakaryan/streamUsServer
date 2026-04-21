package com.example.streamusserver.market.dto.response;

import java.util.List;


public class AdvertisementSearchResponseDto {
    private boolean error;
    private int itemCount;
    private String query;
    private long itemId;
    private List<AdvertisementResponseDto> items;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public List<AdvertisementResponseDto> getItems() {
        return items;
    }

    public void setItems(List<AdvertisementResponseDto> items) {
        this.items = items;
    }
}
