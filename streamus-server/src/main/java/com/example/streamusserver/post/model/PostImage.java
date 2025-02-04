package com.example.streamusserver.post.model;

import com.example.streamusserver.post.model.enums.ImageType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "post_images")
@Data
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String imageUrl;

    @Column
    private Integer orderIndex;

    @Enumerated(EnumType.STRING)
    private ImageType type;

}

