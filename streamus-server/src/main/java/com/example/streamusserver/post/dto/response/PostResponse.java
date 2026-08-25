package com.example.streamusserver.post.dto.response;

import com.example.streamusserver.dto.MediaItemDTO;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.model.MediaItem;

import java.util.List;

public class PostResponse {
    private Long id;
    private String fromUserUsername;
    private int itemType;
    private String fromUserPhotoUrl;
    private Long fromUserId;
    private String postText;
    private String timeAgo;
    private int likeCount;
    private String previewImgUrl;
    private int commentsCount;
    private UserProfile owner;

    private List<MediaItemResponseDto> mediaItem;

    private List<CommentResponseDto> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromUserUsername() {
        return fromUserUsername;
    }

    public void setFromUserUsername(String fromUserUsername) {
        this.fromUserUsername = fromUserUsername;
    }

    public String getFromUserPhotoUrl() {
        return fromUserPhotoUrl;
    }

    public void setFromUserPhotoUrl(String fromUserPhotoUrl) {
        this.fromUserPhotoUrl = fromUserPhotoUrl;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPreviewImgUrl() {
        return previewImgUrl;
    }

    public void setPreviewImgUrl(String previewImgUrl) {
        this.previewImgUrl = previewImgUrl;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public UserProfile getOwner() {
        return owner;
    }

    public void setOwner(UserProfile owner) {
        this.owner = owner;
    }

    public List<MediaItemResponseDto> getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(List<MediaItemResponseDto> mediaItem) {
        this.mediaItem = mediaItem;
    }

    public List<CommentResponseDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponseDto> comments) {
        this.comments = comments;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
