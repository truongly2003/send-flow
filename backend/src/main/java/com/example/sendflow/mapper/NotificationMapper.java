package com.example.sendflow.mapper;

import com.example.sendflow.dto.NotificationDto;
import com.example.sendflow.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationDto notificationToNotificationDto(Notification notification);
}
