package com.example.sendflow.repository;

import com.example.sendflow.entity.SendLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendLogRepository extends JpaRepository<SendLog, Long> {
    Page<SendLog> findAllByCampaignIdOrderBySentAtDesc(Long campaignId, Pageable pageable);
}
