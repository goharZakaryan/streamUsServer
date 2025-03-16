package com.example.streamusserver.post.dto.response;


import com.example.streamusserver.model.UserProfile;

import java.time.LocalDateTime;


public class CommentResponseDto {

    private long id;
    private boolean error;

    private Long userId;
    private Long postId;
    private Long itemId, fromUserId, itemFromUserId, replyToUserId;
    private int fromUserState, fromUserVerified, createAt;
    private String comment, replyToUserUsername, replyToUserFullname, timeAgo;
    private UserProfile owner;
    private String username;
    private String userAvatar;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean edited;
    private int likeCount;
    private Long parentCommentId;

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String usernameAvatar) {
        this.userAvatar = usernameAvatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isEdited() {
        return edited;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CommentResponseDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public int getFromUserState() {
        return fromUserState;
    }

    public void setFromUserState(int fromUserState) {
        this.fromUserState = fromUserState;
    }

    public int getFromUserVerified() {
        return fromUserVerified;
    }

    public void setFromUserVerified(int fromUserVerified) {
        this.fromUserVerified = fromUserVerified;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReplyToUserUsername() {
        return replyToUserUsername;
    }

    public void setReplyToUserUsername(String replyToUserUsername) {
        this.replyToUserUsername = replyToUserUsername;
    }

    public String getReplyToUserFullname() {
        return replyToUserFullname;
    }

    public void setReplyToUserFullname(String replyToUserFullname) {
        this.replyToUserFullname = replyToUserFullname;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public UserProfile getOwner() {
        return owner;
    }

    public void setOwner(UserProfile owner) {
        this.owner = owner;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getItemFromUserId() {
        return itemFromUserId;
    }

    public void setItemFromUserId(Long itemFromUserId) {
        this.itemFromUserId = itemFromUserId;
    }

    public Long getReplyToUserId() {
        return replyToUserId;
    }

    public void setReplyToUserId(Long replyToUserId) {
        this.replyToUserId = replyToUserId;
    }
}
