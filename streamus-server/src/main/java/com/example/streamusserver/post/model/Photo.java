package com.example.streamusserver.post.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String selectedPhotoFileName;
    private String photoUrl;
    @OneToOne
    @JoinColumn(name = "media_id")
    private Media post;
}
