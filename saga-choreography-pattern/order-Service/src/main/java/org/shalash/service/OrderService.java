package org.shalash.service;

import lombok.RequiredArgsConstructor;
import org.shalash.dto.OrderRequestDto;
import org.shalash.event.Order;
import org.shalash.event.PaymentFailed;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    public final KafkaTemplate<String , Order> kafkaTemplate;

    public String createOrder(OrderRequestDto orderRequestDto) {
        System.out.println("order request created: " + orderRequestDto.toString());

        kafkaTemplate.send("order-created", new Order(orderRequestDto.getOrderId(),
                orderRequestDto.getUserId(),
                orderRequestDto.getProductId(),
                orderRequestDto.getAmount(),
                new java.util.Date())
        );
        return "Order created successfully";
    }

    @KafkaListener(topics = "payment-failed")
    public void paymentFailed(PaymentFailed paymentFailed) {
        System.out.println("payment failed: " + paymentFailed.getSecret());
    }


}
