package org.shalash.config;

import lombok.RequiredArgsConstructor;
import org.shalash.event.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final KafkaTemplate<String, Order> kafkaTemplate;


    public void send(Order message) {
        kafkaTemplate.send("my-topic", message);
        System.out.println("Sent: " + message);
    }
}

