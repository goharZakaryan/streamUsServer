package com.example.streamusserver.post.model;

import com.example.streamusserver.model.UserProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String selectedAudioFileName;
    private String audioUrl;

    @ManyToOne
    @JoinColumn(name = "media_id")
    @JsonIgnore
    private Media media;
    private String duration;
    @ManyToOne
    @JoinColumn(name = "userProfile_id")
    private UserProfile userProfile;

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getSelectedAudioFileName() {
        return selectedAudioFileName;
    }

    public void setSelectedAudioFileName(String selectedAudioFileName) {
        this.selectedAudioFileName = selectedAudioFileName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

}
