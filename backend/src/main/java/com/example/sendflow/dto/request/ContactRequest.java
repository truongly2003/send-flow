package com.example.sendflow.dto.request;

import com.example.sendflow.enums.ContactStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {
    private Long contactListId;
    private String name;
    private String phone;
    private String email;
    private ContactStatus status;
}
