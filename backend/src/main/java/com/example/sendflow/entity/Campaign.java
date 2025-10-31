package com.example.sendflow.entity;


import com.example.sendflow.enums.CampaignStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private ContactList contactList;

    private LocalDateTime scheduleTime;
    private String name;
    private String messageContent;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status = CampaignStatus.DRAFT;

    private LocalDateTime createAt=LocalDateTime.now();
    private LocalDateTime updateAt=LocalDateTime.now();

}
