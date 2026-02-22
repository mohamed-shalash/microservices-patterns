package org.shalash.outbox_pattern.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.shalash.outbox_pattern.entity.Order;
import org.shalash.outbox_pattern.entity.OutboxEvent;
import org.shalash.outbox_pattern.repository.OrderRepository;
import org.shalash.outbox_pattern.repository.OutBoxRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OutBoxRepository outBoxRepository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Transactional
    public void CreateOrder(Order order) {
        orderRepository.save(order);
        OutboxEvent outboxEvent = OutboxEvent.builder()
                .aggregateId(order.getId())
                .eventType("Order")
                .payload(convertToJson(order))
                .processed(false)
                .build();
        outBoxRepository.save(outboxEvent);
        System.out.println("Order saved");

    }


//    @Scheduled(fixedDelay = 5000)
//    @Transactional
//    public void publishEvents() {
//
//        List<OutboxEvent> events = outBoxRepository.findByProcessedFalse();
//
//        for (OutboxEvent event : events) {
//
//            kafkaTemplate.send("order-topic", convertFromJson(event.getPayload())).get();
//
//            event.setProcessed(true);
//        }
//    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishEvents() {

        List<OutboxEvent> events = outBoxRepository.findByProcessedFalse();

        for (OutboxEvent event : events) {
            try {
                kafkaTemplate.send("order-topic", convertFromJson(event.getPayload())).get();
                event.setProcessed(true);
                outBoxRepository.save(event);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted while sending event: " + e.getMessage());
            } catch (ExecutionException e) {
                System.err.println("Failed to send Kafka event: " + e.getCause());
                // ممكن تعمل retry أو تسيب الـ processed = false عشان retry بعدين
            }
        }
    }




    private String convertToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting object to JSON", e);
        }
    }
    private Order convertFromJson(String json) {
        try {
            return objectMapper.readValue(json, Order.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to object", e);
        }
    }
}
