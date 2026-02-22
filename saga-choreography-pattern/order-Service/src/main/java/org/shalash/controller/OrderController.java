package org.shalash.controller;

import lombok.RequiredArgsConstructor;
import org.shalash.dto.OrderRequestDto;
import org.shalash.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/create")
    public String createOrder() {
        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .orderId(1)
                .userId(1)
                .productId(1)
                .date(new java.util.Date())
                .build();
        return orderService.createOrder(orderRequestDto);
    }
}
