package com.dishdash.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private Clock clock;

    // The kitchen starts each day from a fixed set of seeded orders.
    @Test
    void seedsOrders() {
        assertThat(orderService.getOrders()).hasSize(18);
    }

    // Moving an order to a new status must survive a re-read.
    @Test
    void statusUpdatePersistsAcrossReads() {
        Long id = orderService.getOrders().get(0).id();
        orderService.updateStatus(id, OrderStatus.DELIVERED);
        OrderDto reloaded = orderService.getOrders().stream()
                .filter(o -> o.id().equals(id))
                .findFirst()
                .orElseThrow();
        assertThat(reloaded.status()).isEqualTo(OrderStatus.DELIVERED);
    }

    // "Revenue today" is the sum of today's orders that were not cancelled.
    @Test
    void revenueTodayExcludesCancelled() {
        StatsDto stats = orderService.stats();
        long expected = orderService.getOrders().stream()
                .filter(o -> isToday(o.createdAt()) && o.status() != OrderStatus.CANCELLED)
                .mapToLong(OrderDto::amountCents)
                .sum();
        assertThat(stats.revenueTodayCents()).isEqualTo(expected);
    }

    private boolean isToday(Instant createdAt) {
        LocalDate today = LocalDate.now(clock);
        LocalDate date = createdAt.atZone(ZoneOffset.UTC).toLocalDate();
        return date.equals(today);
    }
}
