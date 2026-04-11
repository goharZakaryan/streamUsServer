package com.example.streamusserver.market.entity;

import com.example.streamusserver.model.UserProfile;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Advertisement {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private Double price;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private AdType type;

    @ManyToOne
    private UserProfile owner;

    private LocalDateTime createdAt;
}
