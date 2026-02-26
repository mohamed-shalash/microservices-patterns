package org.shalash.controller;

import lombok.RequiredArgsConstructor;
import org.shalash.entity.Order;
import org.shalash.service.OrderCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderCommandService orderCommandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void getAllOrders(@RequestBody Order order) {
        orderCommandService.createOrder(order.getProduct(), order.getQuantity());
    }
}
