package org.shalash.controller;

import lombok.RequiredArgsConstructor;
import org.shalash.service.OrderAggregatorService;
import org.shalash.response.OrderDetailsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderAggregatorController {

    private final OrderAggregatorService service;

    @GetMapping("/{id}")
    public Mono<OrderDetailsResponse> getDetails(@PathVariable Long id) {
        return service.getOrderDetails(id);
    }
}