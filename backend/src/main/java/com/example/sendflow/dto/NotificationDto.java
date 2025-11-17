package com.example.sendflow.dto;

import com.example.sendflow.enums.NotificationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private String title;
    private String body;
    private boolean isRead;
    private LocalDateTime created;
    private NotificationStatus status;
}
