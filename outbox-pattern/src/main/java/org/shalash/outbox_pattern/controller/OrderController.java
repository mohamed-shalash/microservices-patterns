package org.shalash.outbox_pattern.controller;

import lombok.RequiredArgsConstructor;
import org.shalash.outbox_pattern.entity.Order;
import org.shalash.outbox_pattern.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order")
    public String order() {
        //orderService.Order();
        orderService.CreateOrder(new Order((int)Math.random(), "iphone", "egypt", "200"));
        return "Order";
    }
}
