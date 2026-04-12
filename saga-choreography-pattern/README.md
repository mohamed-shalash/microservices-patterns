# Saga Pattern – Choreography

In a **microservices architecture**, a single business transaction often spans multiple services.
Traditional distributed transactions (2PC) are not suitable for microservices because they create tight coupling and reduce scalability.

The **Saga Pattern** solves this by splitting a transaction into **multiple local transactions** coordinated through **events**.

In **Choreography**, there is **no central orchestrator**.
Each service **reacts to events and publishes new events**.

---

## Architecture Flow

```
+--------+
| client |
+--------+
    |
    v
+---------------+
|order service  |  ------        on success
+---------------+          publish payment event
                                     |
                                     v
                                +-----------------+
                                | Payment service | -------  on success
                                +-----------------+       puplish inventory event
                                        |                       |
                                        |                       v
                                     on fail             +-------------------+
        +-----------   publish order rollback event      | Inventory service | ---> on success 
        |                                                +-------------------+         done
		|                                                               |
		|                                                        on fail roll back
		|                                                  publish payment rollback event
        |                        +------------------+                    |
        |                        | Payment rollback |  <-----------------+
        |                        +------------------+ 
		|                                |
		v                    always publish order rollback		
+---------------+                        |
|order rollback |   <--------------------+
+---------------+ 

```

---

## Success Flow

1. **Client** sends a request to **Order Service** to create an order.
2. **Order Service**

   * creates the order
   * publishes a **Payment Event**
3. **Payment Service**

   * processes the payment
   * if successful → publishes an **Inventory Event**
4. **Inventory Service**

   * reserves the product
   * if successful → transaction **completes successfully**

---

## Failure & Compensation Flow

If any step fails, **compensating transactions** are triggered to undo previous steps.

```
+--------+
| client |
+--------+
    |
    v
+---------------+
| Order Service |
+---------------+
        |
        | publish Payment Event
        v
+------------------+
| Payment Service  |
+------------------+
        |
        | fail
        v
 publish Order Rollback Event
        |
        v
+----------------+
| Order Rollback |
+----------------+
```

---

### Inventory Failure Scenario

```
+---------------+
| Order Service |
+---------------+
        |
        v
+------------------+
| Payment Service  |
+------------------+
        |
        v
+-------------------+
| Inventory Service |
+-------------------+
        |
      fail
        |
 publish Payment Rollback Event
        v
+------------------+
| Payment Rollback |
+------------------+
        |
 publish Order Rollback Event
        v
+------------------+
| Order Rollback   |
+------------------+
```

---

## Key Concepts

### Local Transactions

Each service performs its **own database transaction**.

Example:

* Order Service → create order
* Payment Service → charge payment
* Inventory Service → reserve stock

---

### Events

Services communicate through **events** using a message broker such as:

* Kafka
* RabbitMQ
* Pulsar

Examples:

```
OrderCreatedEvent
PaymentSucceededEvent
InventoryReservedEvent
PaymentFailedEvent
OrderRollbackEvent
```

---

### Compensating Transactions

If something fails, the system **reverts previous actions**.

Examples:

| Failure         | Compensation        |
| --------------- | ------------------- |
| Payment fails   | Cancel Order        |
| Inventory fails | Refund Payment      |

---

## Advantages

- Loose coupling between services
- Highly scalable
- No central coordinator
- Works well with **event-driven architecture**

---

## Disadvantages

- Harder to debug
- Event chains can become complex
- Requires **idempotent services**
- Eventual consistency instead of strong consistency
