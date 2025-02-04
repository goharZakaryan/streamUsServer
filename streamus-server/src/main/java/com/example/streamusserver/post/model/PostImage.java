package com.example.streamusserver.post.model;

import com.example.streamusserver.post.model.enums.ImageType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "post_images")

public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    private String imageUrl;

    @Column
    private Integer orderIndex;

    @Enumerated(EnumType.STRING)
    private ImageType type;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }
}

