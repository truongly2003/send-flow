package com.example.sendflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmtpConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String smtpHost;
    private Integer smtpPort;

    private String usernameSmtp;
    private String passwordSmtp;
    private boolean auth;
    private boolean starttls;
    private String encryption;
    private String fromName;
    private String fromEmail;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
