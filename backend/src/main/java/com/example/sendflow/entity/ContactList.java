package com.example.sendflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String description;

    private LocalDateTime createAt=LocalDateTime.now();
    private LocalDateTime updateAt=LocalDateTime.now();

    @Transient
    private Integer totalContacts;

    @OneToMany(mappedBy = "contactList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contact> contacts;
}
