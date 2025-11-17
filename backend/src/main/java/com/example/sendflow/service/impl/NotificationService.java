package com.example.sendflow.service.impl;

import com.example.sendflow.dto.NotificationDto;
import com.example.sendflow.entity.Notification;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.mapper.NotificationMapper;
import com.example.sendflow.repository.NotificationRepository;
import com.example.sendflow.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public List<NotificationDto> getAllNotificationByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                .map(notificationMapper::notificationToNotificationDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteNotificationById(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    @Transactional
    public void deleteAllNotificationByUserId(Long userId) {
        notificationRepository.deleteAllByUserId(userId);
    }

    @Override
    public boolean isRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));
        notification.setRead(false);
        notificationRepository.save(notification);
        return true;
    }

    @Override
    public boolean markAllAsRead(Long userId) {
        List<Notification> unreadNotifications = notificationRepository.findByUserIdAndIsReadFalse(userId);
        unreadNotifications.forEach(notif -> notif.setRead(false));
        notificationRepository.saveAll(unreadNotifications);
        return true;
    }
}
