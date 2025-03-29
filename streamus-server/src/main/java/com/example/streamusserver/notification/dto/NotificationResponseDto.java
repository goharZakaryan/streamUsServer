package com.example.streamusserver.notification.dto;

import com.example.streamusserver.notification.model.Notification;

import java.util.List;

public class NotificationResponseDto {
  private   List<Notify> notifications;
    private Boolean error;

    public NotificationResponseDto(Boolean error) {
        this.error = error;
    }

    public NotificationResponseDto(List<Notify> notifications) {
        this.notifications = notifications;
    }

    public List<Notify> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notify> notifications) {
        this.notifications = notifications;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}
