package com.example.streamusserver.post.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String selectedVideoFileName;
    private String videoUrl;
    @OneToOne
    @JoinColumn(name = "media_id")
    private Media post;
}
