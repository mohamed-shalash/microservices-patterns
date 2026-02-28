package org.shalash.controller;

import lombok.RequiredArgsConstructor;
import org.shalash.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @GetMapping("/all")
    public String getOrders() {
        return orderService.getOrders();
    }
}
