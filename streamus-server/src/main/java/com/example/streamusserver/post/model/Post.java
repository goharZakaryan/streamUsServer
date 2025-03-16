package com.example.streamusserver.post.model;

import com.example.streamusserver.model.UserProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne
    private UserProfile account;
    private String accessToken;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Like> likes = new HashSet<>();
    private long groupId;
    private long postId;
    private long rePostId;
    private int postMode;
    private String postText;
    private String postImg;
    private String postArea;
    private String postCountry;
    private String postCity;
    private double postLat;
    private double postLng;
    private int feeling;
    @OneToMany()
    private List<MediaItem> mediaItem;
    private String videoImgUrl;
    private String videoUrl;
    private LocalDate createdAt;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();
    private int commentsCount;

    // Add getter and setter for comments
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getRePostsCount() {
        return rePostsCount;
    }

    public void setRePostsCount(int rePostsCount) {
        this.rePostsCount = rePostsCount;
    }

    private int rePostsCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile getAccount() {
        return account;
    }

    public void setAccount(UserProfile account) {
        this.account = account;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getRePostId() {
        return rePostId;
    }

    public void setRePostId(long rePostId) {
        this.rePostId = rePostId;
    }

    public int getPostMode() {
        return postMode;
    }

    public void setPostMode(int postMode) {
        this.postMode = postMode;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostImg() {
        return postImg;
    }

    public void setPostImg(String postImg) {
        this.postImg = postImg;
    }

    public String getPostArea() {
        return postArea;
    }

    public void setPostArea(String postArea) {
        this.postArea = postArea;
    }

    public String getPostCountry() {
        return postCountry;
    }

    public void setPostCountry(String postCountry) {
        this.postCountry = postCountry;
    }

    public String getPostCity() {
        return postCity;
    }

    public void setPostCity(String postCity) {
        this.postCity = postCity;
    }

    public double getPostLat() {
        return postLat;
    }

    public void setPostLat(double postLat) {
        this.postLat = postLat;
    }

    public double getPostLng() {
        return postLng;
    }

    public void setPostLng(double postLng) {
        this.postLng = postLng;
    }

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    public List<MediaItem> getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(List<MediaItem> mediaItem) {
        this.mediaItem = mediaItem;
    }

    public String getVideoImgUrl() {
        return videoImgUrl;
    }

    public void setVideoImgUrl(String videoImgUrl) {
        this.videoImgUrl = videoImgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }
}
