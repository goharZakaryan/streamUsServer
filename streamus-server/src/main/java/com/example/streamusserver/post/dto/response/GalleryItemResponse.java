package com.example.streamusserver.post.dto.response;

public class GalleryItemResponse {

        private boolean error;
        private Long itemId;
        private PostResponse item;
        private CommentsResponseDto comments;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public PostResponse getItem() {
        return item;
    }

    public void setItem(PostResponse item) {
        this.item = item;
    }

    public CommentsResponseDto getComments() {
        return comments;
    }

    public void setComments(CommentsResponseDto comments) {
        this.comments = comments;
    }
}
