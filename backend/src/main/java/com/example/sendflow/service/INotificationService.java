package com.example.sendflow.service;

import com.example.sendflow.dto.NotificationDto;

import java.util.List;

public interface INotificationService {
    List<NotificationDto> getAllNotificationByUserId(Long userId);
    void deleteNotificationById(Long notificationId);
    void deleteAllNotificationByUserId(Long userId);
    boolean isRead(Long notificationId);
    boolean markAllAsRead(Long userId);
}
