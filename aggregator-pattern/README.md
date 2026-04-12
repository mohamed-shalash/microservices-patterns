# Aggregator Pattern in Microservices

##  Without Aggregator

```
Frontend
   ├──> Order Service
   ├──> User Service
   └──> Product Service
```

### Problems:

* Multiple network calls from the frontend
* Higher latency
* Increased frontend complexity
* Tight coupling between frontend and multiple services

---

##  With Aggregator

```
Frontend
     │
     ▼
Aggregator Service
   ├──> Order Service
   ├──> User Service
   └──> Product Service
```

The frontend makes **one single call**:

```
GET /api/order-details/123
```

The Aggregator handles communication with downstream services and returns a unified response.

---

# Types of Aggregator

## 1 Simple Aggregator

* Collects responses from multiple services
* Returns them without modification
* No additional processing

## 2 Composite Aggregator

* Collects data from multiple services
* Performs processing or transformation
* Combines data into a custom response model

## 3 Gateway Aggregation

* Sometimes the API Gateway itself performs aggregation
* Useful for simple composition scenarios
* Not ideal for heavy business logic

---

#  Advantages

*  Reduces number of frontend calls
*  Decreases overall latency
*  Maintains Separation of Concerns
*  Keeps frontend simple
*  Centralizes orchestration logic

---

#  Disadvantages

*  Can become a Single Point of Failure
*  May turn into a bottleneck if not implemented asynchronously
*  Requires proper failure handling (timeouts, retries, circuit breakers)
*  Adds another service to maintain

---

# Aggregator vs API Gateway

| Aggregator                   | API Gateway                                    |
| ---------------------------- | ---------------------------------------------- |
| Regular microservice         | Infrastructure layer in front of system        |
| Aggregates and composes data | Handles routing, authentication, rate limiting |
| May contain business logic   | Usually does not contain business logic        |
| Focused on orchestration     | Focused on cross-cutting concerns              |

---

# 🏗 When to Use an Aggregator

Use an Aggregator when:

* A frontend requires data from multiple services for a single view
* You want to reduce frontend complexity
* You need orchestration logic between services
* You want better control over response composition

