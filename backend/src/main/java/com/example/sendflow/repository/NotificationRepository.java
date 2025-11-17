package com.example.sendflow.repository;

import com.example.sendflow.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);

    void deleteAllByUserId(Long userId);

    void deleteByUserId(Long userId);

    List<Notification> findByUserIdAndIsReadFalse(Long userId);
}
