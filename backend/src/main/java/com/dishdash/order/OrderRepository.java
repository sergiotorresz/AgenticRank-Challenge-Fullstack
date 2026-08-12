package com.dishdash.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderByCreatedAtDesc();

    long countByStatusIn(Collection<OrderStatus> statuses);

    @Query("""
        SELECT COALESCE(SUM(o.amountCents), 0) FROM Order o
        WHERE o.createdAt >= :startOfDay AND o.createdAt < :endOfDay
    """)
    long sumRevenueForDay(@Param("startOfDay") Instant startOfDay, @Param("endOfDay") Instant endOfDay);
}
