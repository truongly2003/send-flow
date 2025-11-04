package com.example.sendflow.entity;

import com.example.sendflow.enums.EventStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;


    @Column(columnDefinition = "TEXT")
    private String errorMessage;
    // ID từ nhà cung cấp gửi mail (SendGrid, SES, Mailgun...)
    private String messageId;

    @CreationTimestamp
    private LocalDateTime sentAt;

    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.SENT;

    private int retryCount = 0;
    private LocalDateTime deliveredAt;  // Khi email được provider xác nhận là đã gửi thành công
    private LocalDateTime openedAt;     // Khi người nhận mở mail
    private LocalDateTime clickedAt;    // Khi người nhận click link trong mail
    private LocalDateTime bouncedAt;    // Khi email bị trả lại (bounce)
    private LocalDateTime unsubscribedAt;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String providerResponse;

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
}
