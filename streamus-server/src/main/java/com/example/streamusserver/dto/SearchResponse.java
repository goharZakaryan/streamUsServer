package com.example.streamusserver.dto;


import com.example.streamusserver.model.UserProfile;

import java.util.List;


public class SearchResponse {
    private boolean error;
    private int itemCount;
    private List<UserProfile> items;

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

    public List<UserProfile> getItems() {
        return items;
    }

    public void setItems(List<UserProfile> items) {
        this.items = items;
    }
}
