package org.shalash;

import org.shalash.event.Payment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class InventoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }


    @KafkaListener(topics = "payment-success", groupId = "inventory-group")
    public void paymentFailed(Payment payment) {
        System.out.println("payment scuccess: " + payment.toString());
    }
}