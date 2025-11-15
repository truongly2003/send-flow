package com.example.sendflow.repository;

import com.example.sendflow.entity.SmtpConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmtpConfigRepository extends JpaRepository<SmtpConfig, Long> {
    SmtpConfig findByUserId(Long userId);
}
