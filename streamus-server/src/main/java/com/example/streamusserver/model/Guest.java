package com.example.streamusserver.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "guests")
@Data
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "guest_to_id", nullable = false)
    private UserProfile guestTo;

    @ManyToOne
    @JoinColumn(name = "guest_user_id", nullable = false)
    private UserProfile guestUserId;

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

    public Guest() {
    }
}