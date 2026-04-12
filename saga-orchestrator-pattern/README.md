# Saga Pattern – Orchestration

In a **microservices architecture**, a single business transaction often spans multiple services.
Traditional distributed transactions (2PC) are not suitable for microservices because they introduce **tight coupling and blocking**.

The **Saga Pattern** solves this by breaking a transaction into **multiple local transactions**.

In **Orchestration**, a **central Saga Orchestrator** controls the workflow and decides **which service should execute next**.

Unlike **Choreography**, services **do not react to each other directly**.
Instead, they communicate **through the orchestrator**.

---

# Architecture Flow

```
+--------+
| Client |
+--------+
    |
    v
+---------------+
| Order Service |
+---------------+
    | success
    v
+-----------------+
| Payment Service |
+-----------------+
    | success
    v
+-------------------+
| Inventory Service |
+-------------------+
    | success
    v
   DONE 

Failures & Compensations:

  Payment fails:
   +-----------------+
   | Payment Service |
   +-----------------+
           |
           v
   +---------------+
   | Order Cancel  |
   +---------------+

  Inventory fails:
   +-------------------+
   | Inventory Service |
   +-------------------+
           |
           v
   +-----------------+
   | Refund Payment  |
   +-----------------+
           |
           v
   +---------------+
   | Order Cancel  |
   +---------------+
```

---

# Key Concepts

## Orchestrator

A **central component** responsible for:

* controlling the saga workflow
* sending commands to services
* handling failures
* triggering compensations

Example technologies:

* **Spring Boot Saga Orchestrator**
* **Camunda / Temporal**
* **Axon Framework**

---

## Local Transactions

Each service still executes its **own database transaction**.

Examples:

* Order Service → create order
* Payment Service → charge payment
* Inventory Service → reserve stock

---

## Commands

Instead of events between services, the orchestrator sends **commands**.


```
CreateOrderCommand
ProcessPaymentCommand
ReserveInventoryCommand
CancelOrderCommand
RefundPaymentCommand
```

---

## Compensating Transactions

If something fails, the **orchestrator explicitly triggers rollback operations**.

Examples:

| Failure         | Compensation        |
| --------------- | ------------------- |
| Payment fails   | Cancel Order        |
| Inventory fails | Refund Payment      |

---

# Advantages

* Clear transaction flow
* Easier debugging
* Centralized control
* Easier to implement complex workflows

---

# Disadvantages

* Orchestrator can become a **single point of failure**
* More **coupling to the orchestrator**
* Can become a **bottleneck** in large systems
