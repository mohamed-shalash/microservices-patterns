
# JWT Authentication Between Microservices

This project demonstrates a simple **JWT-based authentication flow** between two microservices:

*  **Auth Service** → Generates JWT tokens
*  **Order Service** → Validates JWT tokens and protects endpoints

The goal is to simulate **service-to-service or client-to-service authentication** in a microservices architecture.

---

# 🏗 Architecture Overview

```text
Client
   │
   ▼
Auth Service  →  Generates JWT
   │
   ▼
Client
   │
   ▼
Order Service →  Validates JWT + Authorizes request
```

---

#  Auth Service

##  Responsibility

* Authenticate user
* Generate JWT token
* Embed claims (role, id, etc.)

---

##  Dependencies

* Lombok
* JJWT (JWT implementation)

Main libraries used:

* `jjwt-api`
* `jjwt-impl`
* `jjwt-jackson`

---

##  Login Endpoint

```text
POST /auth/login?username=admin&password=1234
```

If credentials are valid:

* A JWT token is generated
* Token contains:

  * `sub` → username
  * `role` → ADMIN
  * `id` → 7
  * expiration → 15 minutes

---

##  Token Structure

The generated JWT includes:

* Subject (username)
* Custom claims:

  * role
  * id
* Issued time
* Expiration time
* Signed with HS256 algorithm

---

#  Order Service

##  Responsibility

* Validate incoming JWT
* Extract claims
* Authorize based on role
* Protect endpoints using Spring Security

---

##  Dependencies

* Spring Boot Security
* JJWT
* Lombok

---

#  Secured Endpoint

```text
GET /orders/all
```

Accessible only if:

* JWT is valid
* User has role `ADMIN`

---

#  Authentication Flow

## Step 1 — Login

Client calls:

```text
POST /auth/login
```

Receives:

```text
JWT_TOKEN
```

---

## Step 2 — Call Protected API

Client sends request:

```text
GET /orders/all
Authorization: Bearer <JWT_TOKEN>
```

---

## Step 3 — JWT Filter Execution

1. Extract `Authorization` header
2. Remove `"Bearer "` prefix
3. Validate token signature
4. Extract:

   * username
   * role
5. Create `UsernamePasswordAuthenticationToken`
6. Set authentication in `SecurityContext`
7. Continue filter chain

If token is invalid:

* Return `401 Unauthorized`

---

#  Security Configuration

### Access Rules

| Endpoint        | Access                |
| --------------- | --------------------- |
| `/orders/**`    | Requires `ADMIN` role |
| Other endpoints | Must be authenticated |

Spring Security is configured to:

* Disable CSRF
* Add custom `JwtFilter`
* Enforce role-based authorization

---

#  Important Notes

## 1 Shared Secret

Both services use the same secret:

```text
my-super-secret-key-my-super-secret-key
```

> *In production:*

* Store in environment variables
* Use asymmetric keys (RSA) instead of shared secret
* Use a centralized Identity Provider (Keycloak, Auth0, etc.)

---

## 2 Role Handling

JWT contains:

```text
role = ADMIN
```

Spring Security automatically expects:

```text
ROLE_ADMIN
```

So we prefix:

```text
"ROLE_" + role
```

---

## 3 Token Expiration

Token validity:

```
15 minutes
```

After expiration:

* Token becomes invalid
* Client must re-authenticate

