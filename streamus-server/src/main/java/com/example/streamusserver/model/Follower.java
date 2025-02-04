package com.example.streamusserver.model;

import com.example.streamusserver.model.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "friend_requests")
@Data
@Builder
@AllArgsConstructor
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private UserProfile follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private UserProfile following;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    public Follower() {

    }
}
