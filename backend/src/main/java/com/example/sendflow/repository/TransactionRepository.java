package com.example.sendflow.repository;

import com.example.sendflow.entity.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("""
                SELECT t.plan.name,
                        COUNT(t.user.id),
                        SUM(t.amount)
                FROM Transaction t WHERE t.paymentStatus = 'SUCCESS'
                AND YEAR(t.createdAt) = :year
                AND MONTH(t.createdAt) = :month
                GROUP BY t.plan.name
                ORDER BY SUM(t.amount) DESC
            """)
    List<Object[]> getRevenueByPlan(@Param("year") int year, @Param("month") int month);
    Optional<Transaction> findByReference(String reference);
    // Custom: Tìm pending của user (nếu cần check duplicate)
    Optional<Transaction> findByUserIdAndPaymentStatus(Long userId, TransactionStatus status);
}
