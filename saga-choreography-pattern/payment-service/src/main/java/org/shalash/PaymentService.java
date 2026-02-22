package org.shalash;

import lombok.RequiredArgsConstructor;
import org.shalash.event.Order;
import org.shalash.event.Payment;
import org.shalash.event.PaymentFailed;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@RequiredArgsConstructor
public class PaymentService {
    public static void main(String[] args) {
        SpringApplication.run(PaymentService.class, args);
    }

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order-created")
    public void listen(Order order) {
        boolean success = Math.random() > 0.5;

        if (success) {
            System.out.println("Payment Success");
            kafkaTemplate.send("payment-success",
                    new Payment((int) Math.random(), order.getOrderId(), order.getUserId(), order.getProductId(),5, new java.util.Date()));
        } else {
            System.out.println("Payment Failed");

            kafkaTemplate.send("payment-failed",
                    new PaymentFailed((int) Math.random(), order.getOrderId(), order.getUserId(), order.getProductId(),5, "failed here 😢"));
        }
    }
}