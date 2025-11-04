package com.example.sendflow.dto.response;

import com.example.sendflow.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendLogResponse {
    private Long id;

    private Long contactId;
    private String recipientEmail;
    private String contactName;

    private EventStatus status;
    private String messageId;

    private LocalDateTime sentAt;
    private String providerResponse;
    private int retryCount = 0;

    //    tracking
    private LocalDateTime deliveredAt;  // Khi email được provider xác nhận là đã gửi thành công
    private LocalDateTime openedAt;     // Khi người nhận mở mail
    private LocalDateTime clickedAt;    // Khi người nhận click link trong mail
    private LocalDateTime bouncedAt;    // Khi email bị trả lại (bounce)
    private LocalDateTime unsubscribedAt;
}
