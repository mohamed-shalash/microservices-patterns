
# Outbox Pattern with Spring Boot & Kafka

> A learning module explaining how to solve the **Dual Write Problem** in microservices using the Outbox Pattern.

---

# The Problem Outbox Solves

Imagine this scenario:

1. Save order in the database
2. Send event to Kafka

Now imagine this happens:

* The database save succeeds 
* The application crashes 
* Kafka message is never sent

 Result:

* The order exists in the database
* But no event was published
* The system becomes **inconsistent**

This problem is called:

>  **Dual Write Problem**

Because we wrote to two different systems:

* Database
* Kafka

Without a single transaction covering both.

---

# The Solution: Outbox Pattern

Instead of sending the Kafka message directly, we:

1. Save the Order in the database
2. Save an Event inside an `outbox` table
3. A background process reads unprocessed events
4. Sends them to Kafka
5. Marks them as processed

Now everything happens inside **one database transaction** 

No inconsistency.

---

#  Architecture Overview

```
Application
   |
   | (Single DB Transaction)
   v
Orders Table
Outbox Table
   |
   | (Background Publisher)
   v
Kafka
```

---

#  Step 1 — Create Outbox Table

The Outbox table contains:

* Event ID
* Aggregate ID (Order ID)
* Event Type
* Payload (JSON)
* Processed flag

This table acts as a temporary event store until events are safely published.

---

#  Step 2 — Save Order + Outbox Event (Same Transaction)

When creating an order:

* Save the order normally
* Create an OutboxEvent record
* Store event payload as JSON
* Set `processed = false`

Important:
This must be wrapped in a **single database transaction**.

If anything fails → everything rolls back.

---

#  Step 3 — Background Publisher

A scheduled task:

* Runs every few seconds
* Reads events where `processed = false`
* Publishes them to Kafka
* Marks them as `processed = true`

If publishing fails:

* Leave `processed = false`
* It will retry later

---

#  Full Flow

1. Client creates order
2. Order + Outbox event saved atomically
3. Scheduler picks event
4. Event sent to Kafka
5. Event marked as processed
6. Consumer receives event

---


* Database guarantees atomicity
* Kafka publishing is separated
* Retry is possible
* No message is lost

We avoid the Dual Write Problem entirely.

---

# Outbox vs Saga

| Saga                             | Outbox                          |
| -------------------------------- | ------------------------------- |
| Manages distributed transactions | Solves dual write problem       |
| Handles compensation logic       | Ensures atomic event publishing |
| Coordinates multiple services    | Guarantees reliable messaging   |

 In real systems, they are often used together.

---

# Technology Stack

* Spring Boot
* Spring Data JPA
* PostgreSQL
* Apache Kafka
* Jackson (JSON serialization)
* Scheduler for background publishing

