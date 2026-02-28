package org.shalash.controller;

import lombok.RequiredArgsConstructor;
import org.shalash.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {

        if (username.equals("admin") && password.equals("1234")) {
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of("access_token", token));
        }

        return ResponseEntity.status(401).build();
    }
}
