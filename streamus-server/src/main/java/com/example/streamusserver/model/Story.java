package com.example.streamusserver.model;

import com.example.streamusserver.post.model.Like;
import com.example.streamusserver.post.model.enums.ImageType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "stories")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String mediaUrl;
    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Like> likes = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private ImageType mediaType; // IMAGE, VIDEO

    private String caption;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Transient
    private boolean viewedByCurrentUser;

    @Column(nullable = false)
    private LocalDateTime expiresAt; // Stories expire after 24 hours

    private boolean isActive = true;
    @OneToMany
    private Set<UserProfile> viewedBy = new HashSet<>();
    private int viewerCount;
    private Boolean viewed;

    // Constructors, getters, setters
    public Story() {
    }

    public Story(Long userId, String mediaUrl, ImageType mediaType) {
        this.userId = userId;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusHours(24);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public ImageType getMediaType() {
        return mediaType;
    }

    public void setMediaType(ImageType mediaType) {
        this.mediaType = mediaType;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<UserProfile> getViewedBy() {
        return viewedBy;
    }

    public void setViewedBy(Set<UserProfile> viewedBy) {
        this.viewedBy = viewedBy;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public boolean isViewedByCurrentUser() {
        return viewedByCurrentUser;
    }

    public void setViewedByCurrentUser(boolean viewedByCurrentUser) {
        this.viewedByCurrentUser = viewedByCurrentUser;
    }

    public int getViewerCount() {
        return viewerCount;
    }

    public void setViewerCount(int viewerCount) {
        this.viewerCount = viewerCount;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }
}
