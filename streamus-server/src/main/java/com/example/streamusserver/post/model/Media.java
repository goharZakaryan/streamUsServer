package com.example.streamusserver.post.model;

import com.example.streamusserver.post.model.enums.MediaType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Post post;
    @Enumerated(EnumType.STRING)
    private MediaType type;
    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL)
    private List<Audio> audios;
}
