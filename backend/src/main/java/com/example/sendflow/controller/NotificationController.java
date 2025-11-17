package com.example.sendflow.controller;

import com.example.sendflow.dto.NotificationDto;
import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final INotificationService notificationService;

    // lấy tất cả thông báo
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationDto>>> getAllNotifications(@PathVariable Long userId) {
        List<NotificationDto> notifications = notificationService.getAllNotificationByUserId(userId);
        ApiResponse<List<NotificationDto>> apiResponse = ApiResponse.<List<NotificationDto>>builder()
                .code(2000)
                .message("success")
                .data(notifications)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    // Xoá một thông báo theo userId
    @DeleteMapping("{notificationId}")
    public ResponseEntity<Void> deleteNotifications(@PathVariable Long notificationId) {
        notificationService.deleteNotificationById(notificationId);
        return ResponseEntity.noContent().build();
    }

    // Xoá tất cả thông báo của user
    @DeleteMapping("/user/{userId}/all")
    public ResponseEntity<Void> deleteAllNotifications(@PathVariable Long userId) {
        notificationService.deleteAllNotificationByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    // Đánh dấu một thông báo là đã đọc
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<Boolean>> markAsRead(@PathVariable Long notificationId) {
        boolean isRead= notificationService.isRead(notificationId);
        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                .code(2000)
                .message("success")
                .data(isRead)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    // Đọc tất thông báo là đã đọc
    @PutMapping("/{userId}/read-all")
    public ResponseEntity<ApiResponse<Boolean>> markAsReadAll(@PathVariable Long userId) {
        boolean isRead= notificationService.markAllAsRead(userId);
        ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                .code(2000)
                .message("success")
                .data(isRead)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
