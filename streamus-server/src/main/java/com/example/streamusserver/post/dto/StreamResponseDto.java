package com.example.streamusserver.post.dto;

import com.example.streamusserver.post.model.Like;
import com.example.streamusserver.post.model.Post;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class StreamResponseDto {
    private boolean error;

    private int itemId;

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


    public boolean isViewMore() {
        return viewMore;
    }
}
