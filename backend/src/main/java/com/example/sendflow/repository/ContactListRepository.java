package com.example.sendflow.repository;

import com.example.sendflow.entity.ContactList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactListRepository extends JpaRepository<ContactList, Long> {
    List<ContactList> findAllByUserId(Long userId);
}
