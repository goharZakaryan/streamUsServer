package com.example.streamusserver.post.model;

import com.example.streamusserver.model.UserProfile;
import jakarta.persistence.*;

@Entity
public class UserAudio {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private UserProfile user;

    @ManyToOne
    private Audio audio;

    private boolean favorite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
