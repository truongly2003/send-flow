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
    private String id;
    private String contactEmail;
    private LocalDateTime sentAt;
    private EventStatus status = EventStatus.SENT;
    private String providerResponse;
}
