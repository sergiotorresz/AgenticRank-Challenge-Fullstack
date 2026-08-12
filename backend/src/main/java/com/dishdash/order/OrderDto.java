package com.dishdash.order;

import java.time.Instant;

public record OrderDto(
        Long id,
        String reference,
        String customer,
        long amountCents,
        OrderStatus status,
        Instant createdAt
) {
    public static OrderDto from(Order order) {
        return new OrderDto(
                order.getId(),
                order.getReference(),
                order.getCustomer(),
                order.getAmountCents(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}
