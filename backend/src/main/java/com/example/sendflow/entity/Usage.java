package com.example.sendflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    private String period;
    private Integer setCount=0;
    private Integer recipient=0;

    private LocalDateTime createAt=LocalDateTime.now();
    private LocalDateTime updateAt=LocalDateTime.now();
}

