package com.example.streamusserver.notification.repository;

import com.example.streamusserver.notification.model.Notification;
import com.example.streamusserver.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientIdAndIsReadFalse(Long userId);
    boolean existsByRecipientIdAndIsReadFalse(Long userId);

    void deleteAllByPost(Post post);
}
