package com.example.sendflow.repository;

import com.example.sendflow.entity.Subscription;
import com.example.sendflow.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("SELECT s FROM Subscription s JOIN FETCH s.plan p " +
            "WHERE s.user.id = :userId " +
            "AND s.status = :status " +
            "AND s.endTime > :now " +
            "ORDER BY s.startTime DESC LIMIT 1")
    Optional<Subscription> findCurrentByUserId(@Param("userId") Long userId,
                                               @Param("status") SubscriptionStatus status,
                                               @Param("now") LocalDateTime now);
    // Kiểm tra gói đăng ký còn hoạt động không
    @Query("SELECT s FROM Subscription s WHERE s.user.id = :userId " +
           "AND s.status = :status AND s.startTime <= :now AND s.endTime >= :now")
    Optional<Subscription> findActiveSubscription(Long userId,SubscriptionStatus status, LocalDateTime now);
}
