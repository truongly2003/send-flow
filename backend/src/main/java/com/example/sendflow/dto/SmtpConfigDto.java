package com.example.sendflow.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailJobMessage implements Serializable {
    private Long sendLogId;
    private Long userId;
    private Long campaignId;
    private String email;
    private String subject;
    private String html;

}
