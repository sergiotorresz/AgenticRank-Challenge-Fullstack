package com.dishdash.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<OrderDto> getOrders() {
        return orderService.getOrders();
    }

    @PutMapping("/orders/{id}/status")
    public OrderDto updateStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
        OrderStatus status = parseStatus(request == null ? null : request.status());
        return orderService.updateStatus(id, status);
    }

    @GetMapping("/stats")
    public StatsDto stats() {
        return orderService.stats();
    }

    private OrderStatus parseStatus(String raw) {
        if (raw == null) {
            throw new InvalidStatusException("null");
        }
        try {
            return OrderStatus.valueOf(raw);
        } catch (IllegalArgumentException ex) {
            throw new InvalidStatusException(raw);
        }
    }
}
