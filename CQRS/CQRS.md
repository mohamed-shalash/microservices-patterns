
#  CQRS with Dual Database Strategy (Spring Boot)
>  Note: CQRS can be implemented in a single service and database, but I separated them here for educational and architectural clarity purposes.


>  Note: Project upove is based on Event-Driven
##  Overview

This project demonstrates **CQRS (Command Query Responsibility Segregation)** using:

- Separate Command and Query layers
- Separate Write and Read databases
- Independent transaction managers
- Multiple synchronization strategies


#  Architecture

```

OrderService
├── Command Layer  → write_db
└── Query Layer    → read_db

```

## Query Side (Read)

- Handles Read operations only
- Uses `read_db`
- Optimized for query performance
- Uses read-only transactions

## Command Side (Write)

- Handles Create / Update / Delete operations
- Uses `write_db` as the primary source of truth
- Has its own EntityManager and TransactionManager
---

#  Database Design

| Database | Purpose |
|----------|---------|
| write_db | Primary database |
| read_db  | Read-optimized database |

---

#  Synchronization Strategies

When using separate databases (or services), the read database must stay synchronized with the write database.

There are three common approaches:

---

## 1 Database Replication (Primary → Replica)

```
Write Service  →  write_db (Primary)
                        |
                   Replication
                        ↓
                  read_db (Replica)
                        ↑
                  Query Service
```

###  Pros

* Simple infrastructure-level solution
* Fast
* Good for scaling reads

###  Cons

* Tightly coupled to database technology (means you will need to do it from database engine layer)
* Replication lag may occur
* Not ideal for complex microservices

---

## 2 Event-Driven Sync using Kafka (Recommended for Microservices)

```
OrderCommandService
        |
save in write_db
        |
publish "OrderCreated" event
        |
       Kafka
        |
OrderQueryService (listener)
        |
save in read_db
```

### Command Service Example

```java
@Transactional
public void createOrder(Order order) {
    orderRepository.save(order);
    kafkaTemplate.send("order-created", order);
}
```

### Query Service Listener

```java
@KafkaListener(topics = "order-created")
public void handleOrderCreated(Order order) {

    OrderView view = new OrderView(
        order.getId(),
        order.getProduct(),
        order.getQuantity()
    );

    orderViewRepository.save(view);
}
```

###  Pros

* Loose coupling
* Highly scalable
* Supports Saga pattern
* Easy to extend with more services

###  Cons

* Eventual consistency
* More infrastructure complexity

---

## 3 Scheduled Sync Job

```
Every 1 minute:
   copy new data from write_db
   to read_db
```

### Example

```java
@Scheduled(fixedRate = 60000)
public void syncOrders() {

    List<Order> orders = writeRepository.findAll();

    List<OrderView> views = orders.stream()
        .map(o -> new OrderView(
            o.getId(),
            o.getProduct(),
            o.getQuantity()
        ))
        .toList();

    readRepository.saveAll(views);
}
```

dont forget to Add :

```java
@EnableScheduling
```

iam always forget

###  Pros

* Very simple
* No external infrastructure

###  Cons

* Not real-time
* Inefficient for large systems

---

# Strategy Comparison

| Strategy      | Real-Time | Microservices Friendly | Complexity |
| ------------- | --------- | ---------------------- | ---------- |
| Replication   | Almost    |  Limited               | Easy       |
| Kafka Events  |  Yes      |  Excellent             | Medium     |
| Scheduled Job |  No       |  Weak                  | Very Easy  |

---

# When To Use What?

* Small monolith → Database Replication
* Scalable microservices → Kafka Event-driven architecture
* Prototype / demo → Scheduled job

---

#  Tech Stack

* Java
* Spring Boot
* Spring Data JPA
* MySQL
* Apache Kafka (optional)
* Docker (optional)

---

# Key Concepts Demonstrated

* CQRS pattern
* Separate write and read models
* Dual datasource configuration
* Independent transaction managers
* Event-driven architecture
* Eventual consistency

---

# 👨‍💻 Author

Mohamed Shalash
Backend Engineer | Distributed Systems Enthusiast




