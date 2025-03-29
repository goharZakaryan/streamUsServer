package com.example.streamusserver.notification.controller;

import com.example.streamusserver.notification.dto.NotificationRequestDto;
import com.example.streamusserver.notification.dto.NotificationResponseDto;
import com.example.streamusserver.notification.model.Notification;
import com.example.streamusserver.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;


    // Get unread notifications for current user
    @PostMapping("/unread")
    public ResponseEntity<NotificationResponseDto> getUnreadNotifications(
            @RequestBody NotificationRequestDto notification) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(notification));
    }

    // Mark notifications as read
    @PostMapping("/mark-read")
    public ResponseEntity<Void> markNotificationsAsRead(
            @RequestBody NotificationRequestDto notificationRequestDto) {
        notificationService.markNotificationsAsRead(notificationRequestDto);
        return ResponseEntity.ok().build();
    }
}
