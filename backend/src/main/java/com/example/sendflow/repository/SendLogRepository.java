package com.example.sendflow.repository;

import com.example.sendflow.entity.SendLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendLogRepository extends JpaRepository<SendLog, Long> {
}
