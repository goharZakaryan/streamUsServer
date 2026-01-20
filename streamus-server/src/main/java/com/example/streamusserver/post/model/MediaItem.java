package com.example.streamusserver.post.model;

import com.example.streamusserver.post.model.enums.ImageType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MediaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String selectedImageFileName;
    private String imageUrl;
    private String audioUrl;
    private String selectedVideoFileName;
    private String videoUrl;
    @Enumerated(EnumType.STRING)
    private ImageType type;
    @ManyToOne
    private Post post;

    public MediaItem() {
    }
}
