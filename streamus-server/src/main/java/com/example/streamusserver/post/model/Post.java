package com.example.streamusserver.post.model;

import com.example.streamusserver.model.UserProfile;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne
    private UserProfile account;
    private String accessToken;
    private long groupId;
    private long postId;
    private long rePostId;
    private int postMode;
    private String postText;
    private String postImg;
    private String postArea;
    private String postCountry;
    private String postCity;
    private double postLat;
    private double postLng;
    private int feeling;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> images;
    private String videoImgUrl;
    private String videoUrl;
}
