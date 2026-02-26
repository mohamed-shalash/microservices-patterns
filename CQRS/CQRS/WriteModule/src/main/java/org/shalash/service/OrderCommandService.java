package org.shalash.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.shalash.entity.Order;
import org.shalash.repository.OrderCommandRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCommandService {

    private final OrderCommandRepository repository;
    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Transactional()
    public void createOrder(String product, int quantity) {
        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(quantity);
        repository.save(order);
        kafkaTemplate.send("order-created", order);
    }
}
