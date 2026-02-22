package org.shalash.config;

import org.shalash.event.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {

    @KafkaListener(topics = "my-topic")
    public void listen(Order message) {
        System.out.println("Received in App 2: " + message.toString());
    }
}
