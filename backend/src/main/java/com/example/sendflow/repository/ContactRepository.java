package com.example.sendflow.repository;

import com.example.sendflow.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findAllByContactListId(Long contactListId, Pageable pageable);
}
