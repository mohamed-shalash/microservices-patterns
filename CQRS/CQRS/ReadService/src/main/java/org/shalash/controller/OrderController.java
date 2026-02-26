package org.shalash.controller;

import lombok.RequiredArgsConstructor;
import org.shalash.entity.Order;
import org.shalash.service.OrderQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderQueryService orderQueryService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderQueryService.getAllOrders();
    }
}
