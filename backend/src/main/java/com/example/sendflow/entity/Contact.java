package com.example.sendflow.entity;

import com.example.sendflow.enums.ContactStatus;
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
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contact_list_id")
    private ContactList contactList;

    private String name;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private ContactStatus status = ContactStatus.SUBSCRIBED;

    private LocalDateTime createAt=LocalDateTime.now();
    private LocalDateTime updateAt=LocalDateTime.now();

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SendLog> sendLogs;

}
