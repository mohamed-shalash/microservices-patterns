package org.shalash.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

// in this case we talk to sidecar service and it will validate token and forward request to order-service
@RestController
@RequestMapping("/orders")
public class AuthProxyController {

    private final RestTemplate restTemplate;

    public AuthProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> proxy(
            @PathVariable String id,
            @RequestHeader(value = "Authorization", required = false) String token
    ) {

        // Validate token
        if (token == null || !token.equals("Bearer my-secret-token")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Token");
        }

        // Forward request to order-service
        String response = restTemplate.getForObject(
                "http://localhost:8080/orders/" + id,
                String.class
        );

        return ResponseEntity.ok(response);
    }
}
