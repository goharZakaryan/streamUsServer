package com.example.streamusserver.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "storyView")
@Data
public class StoryView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "viewer_id", nullable = false)
    private UserProfile viewer;
    @CreationTimestamp
    private LocalDateTime viewedAt;
    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    @Column(name = "verify")
    private int verify;

    @Column(name = "vip")
    private int vip;

    @Column(name = "guest_user_username")
    private String guestUserUsername;

    @Column(name = "guest_user_fullname")
    private String guestUserFullname;

    @Column(name = "guest_user_photo")
    private String guestUserPhoto;

    @Column(name = "time_ago")
    private String timeAgo;

    @Column(name = "online")
    private Boolean online = false;
    @Column(name = "viewed")

    private Boolean viewed;

    public StoryView() {
    }
}