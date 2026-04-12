# JWT & Client Credentials Microservices

## 🔹 Overview

In microservices projects, **authentication and authorization** are crucial. Here we have **two main approaches** to authenticate the 2 services:

1. **JWT Forwarding**
   * User logs in → receives a JWT
   * User calls Order Service and sends the JWT along with the request (in the header)
   * Order Service forwards the JWT to Payment Service
   * Payment Service verifies the JWT and checks the user’s role
   * **User-centric authentication**

2. **Client Credentials**
   * Order Service logs into Auth Server using **client_id and client_secret**
   * Auth Server returns an Access Token
   * Order Service uses the token to call Payment Service
   * **Service-to-service authentication** (no user involved)

---

## 🔹 JWT Forwarding Architecture

```
User
  │
  ▼ (JWT)
Order Service
  │
  ▼ (JWT Forwarding)
Payment Service
```

### How it works:

* Each user receives a JWT after login
* JWT contains a claim `role` (USER, ADMIN, SUPERADMIN)
* Payment Service validates the role using a **JwtFilter**

### How to use:

1. User logs in → receives JWT
    ```bash
        curl --location --request POST 'http://localhost:8080/auth/login?username=admin&password=1234' --header 'Content-Type: text/plain'
    ```
2. Call `/orders` endpoint with Authorization header
    ```bash
        curl --location 'http://localhost:8081/orders/all' --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiIsImlhdCI6MTc3MjEzNzY3NiwiZXhwIjoxNzcyMTM4NTc2fQ.niQwYUVmihZb5ajIQEZ-co6D8pqzE_4kFSczPLpJQw8'
    ```
3. Order Service forwards JWT to Payment Service
4. Payment Service validates JWT and checks if the user is allowed

---

## 🔹 Client Credentials Architecture

```
Order Service
  │
  ▼ (client_id + client_secret)
Auth Server
  │
  ▼ (Access Token)
Payment Service
```

### How it works:

* Order Service requests an Access Token from Auth Server
* Access Token contains a claim `scope` (e.g., `payment:read`)
* Payment Service validates the scope using **JwtFilter**
* No user interaction is needed; this is service-to-service communication

### How to use:

1. Order Service requests Access Token from Auth Server
    ```java
    curl --location 'http://localhost:8080/orders/all' --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJTVVBFUkFETUlOIiwiaWQiOiIyIiwiaWF0IjoxNzcyMTM2NjY5LCJleHAiOjE3NzIxMzc1Njl9.8Y1gTehOls2j8j1DqzTnqW33iAIItLuGfKUlTgRBpJw'
    ```
2. Order Service includes the token in Authorization header when calling Payment Service
3. Payment Service verifies the token and scope

---

## Dependencies

* `io.jsonwebtoken:jjwt-api/impl/jackson` → JWT handling
* `org.projectlombok:lombok` → reduces boilerplate
* `spring-boot-starter-security` → security configuration

---

## Summary

| Feature                | JWT Forwarding                        | Client Credentials           |
| ---------------------- | --------------------------------------| ---------------------------- |
| User needed?           |  Yes                                  |  No                       |
| Authentication flow    | User→ auth → User → Order → Payment   | Order → Auth → Payment       |
| Use case               | User-specific access                  | Service-to-service access    |

---
