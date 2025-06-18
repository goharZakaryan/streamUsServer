package com.example.streamusserver.notification.service.impl;

import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.notification.dto.NotificationRequestDto;
import com.example.streamusserver.notification.dto.NotificationResponseDto;
import com.example.streamusserver.notification.dto.Notify;
import com.example.streamusserver.notification.model.Notification;
import com.example.streamusserver.notification.model.enums.NotificationType;
import com.example.streamusserver.notification.repository.NotificationRepository;
import com.example.streamusserver.notification.service.NotificationService;
import com.example.streamusserver.post.model.Post;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private UserProfileService userProfileService;


    public Notification createFollowRequest(Long senderId, Long targetUserId) {
        Notification notification = new Notification();
        notification.setType(NotificationType.FOLLOW_REQUEST);
        notification.setSenderId(senderId);
        notification.setRecipientId(targetUserId);
        notification.setMessage("New follow request");
        notification.setCreatedAt(LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    public Notification createLikeNotification(Long likerId, Long postId, UserProfile userProfile, Post post) {

        Notification notification = new Notification();
        notification.setType(NotificationType.LIKE);
        notification.setSenderId(likerId);
        notification.setRecipientId(post.getAccount().getId());
        notification.setReferenceId(postId);
        notification.setMessage("Liked your post");
        notification.setCreatedAt(LocalDateTime.now());
        notification.setUserProfile(userProfile);
        notification.setPost(post);
        return notificationRepository.save(notification);
    }

    public Notification createCommentNotification(UserProfile commenterId, Post postId, String commentText) {


        Notification notification = new Notification();
        notification.setType(NotificationType.COMMENT);
        notification.setSenderId(commenterId.getId());
        notification.setRecipientId(postId.getAccount().getId());
        notification.setReferenceId(postId.getId());
        notification.setUserProfile(commenterId);
        notification.setPost(postId);
        notification.setMessage("Commented: " + commentText);
        notification.setCreatedAt(LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    public NotificationResponseDto getUnreadNotifications(NotificationRequestDto userId) {
        if (!jwtUtil.isTokenValid(userId.getAuthToken())) {
            return new NotificationResponseDto(true);
        }
        List<Notification> notifications = notificationRepository.findByRecipientIdAndIsReadFalse(userId.getTargetUserId());
        List<Notify> byRecipientIdAndIsReadFalse = mapNotificationToNotify(notifications);
        notificationRepository.saveAll(
                notifications.stream()
                        .peek(notification -> notification.setRead(true))
                        .toList()
        );

        return new NotificationResponseDto(byRecipientIdAndIsReadFalse);
    }

    @Override
    public boolean notificationExists(Long id) {
        return notificationRepository.existsByRecipientIdAndIsReadFalse(id);
    }

    public void markNotificationsAsRead(NotificationRequestDto notificationRequestDto) {
        List<Notification> notifications = notificationRepository.findAllById(notificationRequestDto.getNotificationIds());

        // Ensure user can only mark their own notifications
        notifications.forEach(notification -> {
            if (!notification.getRecipientId().equals(notificationRequestDto.getTargetUserId())) {
                throw new AccessDeniedException("Cannot mark another user's notifications");
            }
            notification.setRead(true);
        });

        notificationRepository.saveAll(notifications);
    }

    @Override
    public void deleteAllByPost(Post post) {
        notificationRepository.deleteAllByPost(post);
    }

    private List<Notify> mapNotificationToNotify(List<Notification> notifications) {
        return notifications.stream()
                .map(notification -> {
                    Notify notify = new Notify();
//                    notify.setCreateAt(notification.getCreatedAt().toString());
                    notify.setFromUserFullname(notification.getUserProfile().getFullname());
                    notify.setFromUserId(notification.getUserProfile().getId());
                    notify.setFromUserPhotoUrl(notification.getUserProfile().getPhotoUrl());
                    notify.setFromUserUsername(notification.getUserProfile().getUsername());
                    notify.setFromUserState(notification.getUserProfile().getState());
                    notify.setItemId(notification.getPost().getId());

                    notify.setType(notification.getType().name());
                    return notify;
                })
                .collect(Collectors.toList());
    }

}

