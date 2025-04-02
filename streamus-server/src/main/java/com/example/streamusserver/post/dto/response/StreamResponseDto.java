package com.example.streamusserver.post.dto.response;

import com.example.streamusserver.post.model.Post;

import java.util.List;


public class StreamResponseDto {
    private boolean error;

    private int itemId;
    private boolean notification;


    private List<Post> items;
    private boolean viewMore;

    public boolean isError() {
        return error;
    }

    public int getItemId() {
        return itemId;
    }

    public List<Post> getItems() {
        return items;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setItems(List<Post> items) {
        this.items = items;
    }

    public void setViewMore(boolean b) {
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public boolean isViewMore() {
        return viewMore;
    }
}
