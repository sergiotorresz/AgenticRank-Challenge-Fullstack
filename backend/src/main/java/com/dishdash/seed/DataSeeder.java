package com.dishdash.seed;

import com.dishdash.order.Order;
import com.dishdash.order.OrderRepository;
import com.dishdash.order.OrderStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    /**
     * Fixed base instant used both to seed the data and (via the Clock bean) to compute
     * "today", so revenue is reproducible. Do NOT use Instant.now() here.
     */
    public static final Instant TODAY_BASE = Instant.parse("2026-06-01T12:00:00Z");

    private final OrderRepository orderRepository;

    public DataSeeder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (orderRepository.count() > 0) {
            return;
        }

        List<Order> orders = List.of(
                // ---- 12 orders created "today" (2026-06-01) ----
                order("DD-1001", "Acme Corp", 2500, OrderStatus.DELIVERED, minusHours(5)),
                order("DD-1002", "globex", 4200, OrderStatus.PREPARING, minusHours(4)),
                order("DD-1003", "Initech", 1800, OrderStatus.RECEIVED, minusHours(3)),
                order("DD-1004", "umbrella foods", 9600, OrderStatus.READY, minusHours(2)),
                order("DD-1005", "Acme Corp", 3300, OrderStatus.CANCELLED, minusHours(1)),
                order("DD-1006", "Wonka Industries", 12000, OrderStatus.DELIVERED, TODAY_BASE),
                order("DD-1007", "Stark LLC", 800, OrderStatus.RECEIVED, plusHours(1)),
                order("DD-1008", "globex", 5400, OrderStatus.CANCELLED, plusHours(2)),
                order("DD-1009", "Hooli", 2750, OrderStatus.PREPARING, minusHours(6)),
                order("DD-1010", "Soylent Co", 6100, OrderStatus.DELIVERED, minusMinutes(90)),
                order("DD-1011", "Initech", 4800, OrderStatus.READY, plusHours(3)),
                order("DD-1012", "Pied Piper", 3900, OrderStatus.CANCELLED, minusMinutes(30)),

                // ---- 6 orders created on prior days ----
                order("DD-0995", "Acme Corp", 5000, OrderStatus.DELIVERED, minusDaysHours(1, 0)),
                order("DD-0996", "globex", 2200, OrderStatus.DELIVERED, minusDaysHours(1, 3)),
                order("DD-0997", "umbrella foods", 7300, OrderStatus.CANCELLED, minusDaysHours(2, 0)),
                order("DD-0998", "Stark LLC", 8800, OrderStatus.DELIVERED, minusDaysHours(2, 1)),
                order("DD-0999", "Hooli", 1500, OrderStatus.READY, minusDaysHours(3, 0)),
                order("DD-1000", "Wonka Industries", 10500, OrderStatus.DELIVERED, minusDaysHours(4, 0))
        );

        orderRepository.saveAll(orders);
    }

    private static Order order(String reference, String customer, long amountCents,
                               OrderStatus status, Instant createdAt) {
        return new Order(reference, customer, amountCents, status, createdAt);
    }

    private static Instant minusHours(long hours) {
        return TODAY_BASE.minus(Duration.ofHours(hours));
    }

    private static Instant plusHours(long hours) {
        return TODAY_BASE.plus(Duration.ofHours(hours));
    }

    private static Instant minusMinutes(long minutes) {
        return TODAY_BASE.minus(Duration.ofMinutes(minutes));
    }

    private static Instant minusDaysHours(long days, long hours) {
        return TODAY_BASE.minus(Duration.ofDays(days).plusHours(hours));
    }
}
