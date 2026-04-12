
# Apache Kafka Setup with Spring Boot

> This is a learning Repo for developers who are not familiar with Apache Kafka.

This guide explains how to:

* Run Kafka using Docker Compose
* Create topics and partitions
* Understand partitions, topics, and consumer groups
* Configure Spring Boot Producer & Consumer
* Send and receive messages using Kafka

---

# 🐳 Kafka Setup Using Docker

We use **Docker Compose** to run:

* Zookeeper
* Kafka Broker

---

## `docker-compose.yml`

```yaml
version: '3.8'

services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    container_name: zookeeper
    ports:
      - "2181:2181"
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    container_name: kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181

      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092

      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
```

---

## Run Kafka

```bash
docker-compose up -d
```

---

# Create a Topic with Partitions

```bash
kafka-topics --create \
--topic order-topic \
--bootstrap-server localhost:9092 \
--partitions 3 \
--replication-factor 1
```

---

## List All Topics

```bash
kafka-topics --list --bootstrap-server localhost:9092
```

This ensures Kafka is running properly.

---

# Core Kafka Concepts

## Topic

A **logical category (channel)** where messages are published.

Example:

```text
order-topic
payment-topic
notification-topic
```

---

## Partition

An **independent ordered log** inside a topic.

* Messages are distributed across partitions.
* Distribution is usually based on hashing the message key.
* Each partition guarantees order.

---

## Consumer Group (`group-id`)

* Consumers with the same `group-id` belong to the same group.
* Kafka distributes partitions among them.

### Important Behavior

If we have:

* 3 partitions
* 3 consumers (same group)

➡ Each consumer reads from 1 partition.

If:

* 3 partitions
* 4 consumers

➡ One consumer will be idle.

If:

* 3 partitions
* 2 consumers

➡ One consumer reads from 2 partitions.

---

# 📦 Spring Boot Kafka Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-kafka</artifactId>
</dependency>

<!-- JSON processor -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-annotations</artifactId>
    <version>2.15.2</version>
</dependency>
```

---

# ⚙️ Kafka Configuration

---

## 🟢 Producer Configuration (`application.properties`)

```properties
spring.kafka.bootstrap-servers=localhost:9092

#kafka producer config
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
```

---

## 🔵 Consumer Configuration (`application.properties`)

```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=group1
spring.kafka.consumer.auto-offset-reset=earliest


spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=*
```

---

# Kafka Producer (Spring Boot)

```java
@Service
@RequiredArgsConstructor
public class ProducerService {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    public void send(Order message) {
        kafkaTemplate.send("my-topic", message);
        System.out.println("Sent: " + message);
    }
}
```

### What Happens:

* Message is serialized to JSON.
* Sent to topic `my-topic`.
* Kafka assigns it to a partition.

---

# Kafka Consumer (Spring Boot)

```java
@Component
public class ConsumerService {

    @KafkaListener(topics = "my-topic")
    public void listen(Order message) {
        System.out.println("Received in App 2: " + message.toString());
    }
}
```

### What Happens:

* Consumer subscribes to `my-topic`.
* Kafka assigns partitions to the consumer.
* Messages are deserialized automatically into `Order` object.

---

# Full Flow

```text
Producer App
   │
   ▼
Kafka Topic (with partitions)
   │
   ▼
Consumer App
```

1. Producer sends message.
2. Kafka stores it inside a partition.
3. Consumer group reads it.
4. Offset is committed.

---


#  Summary

* Kafka runs via Docker.
* Topics contain partitions.
* Consumers read using group-id.
* Spring Boot simplifies Producer/Consumer implementation.
* Ordering is per partition.
* Scaling depends on partitions.


