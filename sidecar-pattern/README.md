

# Sidecar Pattern in Microservices

## Overview

The **Sidecar Pattern** is an architectural pattern where an additional component (sidecar service) runs alongside a main microservice.

---

## Why Use the Sidecar Pattern?

Instead of duplicating logic across multiple services, you can offload responsibilities such as:

* Logging
* Monitoring
* Authentication
* Retry & Circuit Breaker
* Configuration management
* Service mesh proxy

### Benefits

* Separation of concerns
* Cleaner microservice code
* Reusability
* Easier updates and maintenance
* Better scalability

---

## Architectural Concept

### Basic Flow

```
Client
   |
   v
Sidecar  --->  Microservice
```

### In Kubernetes

```
Pod:
   - Application Container
   - Sidecar Container
```

Both containers run in the same Pod and share:

* Network
* Storage (if needed)
* Lifecycle

---

# Practical Example (Spring Boot)

## Scenario

* `order-service` → Main microservice
* `logging-sidecar` → Handles logging

---

## 1. Order Service

The service does not log internally.
Instead, it sends a request to the sidecar.

```java
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable String id) {

        // Call sidecar for logging
        restTemplate.postForObject(
                "http://localhost:9000/log",
                "Order requested: " + id,
                String.class
        );

        return "Order " + id;
    }
}
```

---

## 2. Logging Sidecar

The sidecar is responsible for logging messages.

```java
@RestController
public class LoggingController {

    @PostMapping("/log")
    public String log(@RequestBody String message) {
        System.out.println("Logged: " + message);
        return "OK";
    }
}
```

---

## What Happens Here?

1. A request hits `order-service`.
2. The service calls the sidecar.
3. The sidecar handles logging.
4. The main service remains clean and focused on business logic.

---

# Real-World Usage

The Sidecar Pattern is commonly used with:

* Service Mesh (e.g., Envoy proxy)
* Security proxies
* Metrics collectors
* Log shippers
* Configuration managers

---

# Let us wrap it up

The Sidecar Pattern helps you:

* Keep microservices clean
* Isolate infrastructure concerns
* Improve maintainability
* Build scalable distributed systems

It is especially powerful in Kubernetes-based architectures.
