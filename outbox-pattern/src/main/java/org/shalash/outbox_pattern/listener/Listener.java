package org.shalash.outbox_pattern.listener;

import org.shalash.outbox_pattern.entity.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @KafkaListener(topics = "order-topic", groupId = "group_id")
    public void listen(Order order) {
        System.out.println("Received Message in group_id: " + order);
    }
}
