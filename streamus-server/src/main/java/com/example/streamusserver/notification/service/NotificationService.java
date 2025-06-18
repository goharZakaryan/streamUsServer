package com.example.streamusserver.notification.service;

import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.notification.dto.NotificationRequestDto;
import com.example.streamusserver.notification.dto.NotificationResponseDto;
import com.example.streamusserver.notification.model.Notification;
import com.example.streamusserver.post.model.Post;

public interface NotificationService {
    Notification createLikeNotification(Long likerId, Long postId, UserProfile userProfile, Post post);

    NotificationResponseDto getUnreadNotifications(NotificationRequestDto notificationRequestDto);

    boolean notificationExists(Long id);

    void markNotificationsAsRead(NotificationRequestDto notificationRequestDto);

    void deleteAllByPost(Post post);

    Notification createCommentNotification(UserProfile commenterId, Post postId, String commentText);
}
