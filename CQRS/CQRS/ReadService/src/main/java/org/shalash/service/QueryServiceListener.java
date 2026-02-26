package org.shalash.service;

import lombok.RequiredArgsConstructor;
import org.shalash.entity.Order;
import org.shalash.repository.OrderQueryRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryServiceListener {
    private final OrderQueryRepository repository;
    @KafkaListener(topics = "order-created")
    public void handleOrderCreated(Order order) {
        System.out.println("Order created here : " + order);
        Order view = new Order(
                order.getProduct(),
                order.getQuantity()
        );

        repository.save(view);
    }
}
