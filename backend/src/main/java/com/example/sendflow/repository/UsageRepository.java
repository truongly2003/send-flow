package com.example.sendflow.repository;

import com.example.sendflow.entity.Usage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsageRepository extends JpaRepository<Usage, Long> {
    @Query("SELECT u FROM Usage u WHERE u.subscription = :subscription AND u.period = :period")
    Optional<Usage> findBySubscriptionAndPeriod(Long subscriptionId, String period);

    @Query("SELECT SUM(u.emailCount) FROM Usage u WHERE u.subscription.user.id = :userId")
    Long getTotalEmailByUser(@Param("userId") Long userId);

//    @Query("""
//        SELECT u  FROM Usage u  WHERE u.subscription.id = :subscriptionId
//    """)
//    Usage findBySubscription(@Param("subscriptionId") Long subscriptionId);
    Usage findBySubscriptionId(Long subscriptionId);
}
