package com.dishdash.order;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class OrderService {

    private static final List<OrderStatus> ACTIVE_STATUSES =
            List.of(OrderStatus.RECEIVED, OrderStatus.PREPARING, OrderStatus.READY);

    private final OrderRepository orderRepository;
    private final Clock clock;

    public OrderService(OrderRepository orderRepository, Clock clock) {
        this.orderRepository = orderRepository;
        this.clock = clock;
    }

    public List<OrderDto> getOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(OrderDto::from)
                .toList();
    }

    public OrderDto updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.setStatus(status);
        return OrderDto.from(order);
    }

    public StatsDto stats() {
        LocalDate today = LocalDate.now(clock);
        Instant startOfDay = today.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endOfDay = today.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        long revenueTodayCents = orderRepository.sumRevenueForDay(startOfDay, endOfDay);
        long activeCount = orderRepository.countByStatusIn(ACTIVE_STATUSES);

        return new StatsDto(revenueTodayCents, activeCount);
    }
}
