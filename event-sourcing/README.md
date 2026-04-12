# Event Sourcing Pattern

## What is Event Sourcing?

**Event Sourcing** is a way of persisting system state differently from the traditional approach.

### Traditional Approach

* Store the **current state** of an entity.
* Example:

  * `Account.balance = 1000`

### Event Sourcing Approach

* Store **every event** that happened to the entity.
* The current state is rebuilt by **replaying all events**.

### Example

| Event Type     | Amount | Time       |
| -------------- | ------ | ---------- |
| AccountCreated | 0      | 2026-02-21 |
| Deposit        | 500    | 2026-02-22 |
| Withdrawal     | 200    | 2026-02-23 |

Final Balance = 0 + 500 − 200 = **300**

 **Key Idea:**
The system does not store the state directly — it stores the **history of changes (events)**.

---

#  Why Event Sourcing?

###  Audit & Traceability

Every change is recorded permanently.

###  Replay & Recovery

If the system crashes, you can replay events to rebuild the state.

###  Temporal Queries

You can ask:

> “What was the account balance on Feb 22?”

###  Integration with Microservices

Events can be published as **event streams** to other services.

---

#  Event Sourcing in Microservices

Event Sourcing is commonly used together with:

## CQRS (Command Query Responsibility Segregation)

Flow:

```
Command → Generates Event → Event Stored → Projection Updated → Query Reads Projection
```

### Example Flow

```
OrderService:
   Command → CreateOrder
   Event → OrderCreated

PaymentService:
   Listens to OrderCreated → Starts payment process

NotificationService:
   Listens to OrderCreated → Sends confirmation email
```

Each service reacts independently to events.

---

#  How State is Built

Instead of storing:

```
Account.balance = 300
```

We store:

```
AccountCreated(0)
Deposit(500)
Withdrawal(200)
```

The current balance is calculated by replaying events.

---

#  Advantages

*  Complete audit history
*  Easy debugging
*  Time-travel capability
*  Natural fit for event-driven microservices
*  High traceability
*  Easy integration with Kafka or message brokers

---

#  Disadvantages

*  Increased complexity
*  Harder debugging without proper tooling
*  Event versioning challenges
*  Requires projections for fast reads
*  Storage grows continuously

---

#  Event Sourcing vs Outbox Pattern

| Feature                     | Event Sourcing                      | Outbox Pattern                      |
| --------------------------- | ----------------------------------- | ----------------------------------- |
| Primary Goal                | Store every state-changing event    | Ensure reliable event publishing    |
| Where events stored         | Event Store (Kafka, specialized DB) | Outbox table inside main DB         |
| Is state built from events? | Yes                                 | No                                  |
| Common Use Case             | Replay, Audit, Temporal Queries     | Reliable messaging between services |
| Complexity                  | Higher                              | Moderate                            |

---

##  Conceptual Difference

### Event Sourcing

* Events **are the source of truth**
* State is derived from events
* Database tables may not store final state directly

### Outbox Pattern

* State is stored normally in tables
* Events are stored only to ensure safe publishing
* Used to avoid dual-write problem

---

# 🧠 When Should You Use Event Sourcing?

Use it when:

* You need strong audit requirements
* You need time-travel queries
* Your system is event-driven
* You’re building complex business workflows
* Domain logic is important and evolving

Avoid it when:

* System is simple CRUD
* No need for audit or replay
* Team lacks experience with distributed systems

---

#  Summary

1. Event Sourcing = Store **events**, not state.
2. State = Result of replaying all events.
3. Works very well with CQRS and Microservices.
4. Provides audit, replay, temporal queries, and integration capabilities.
5. More powerful — but more complex.


