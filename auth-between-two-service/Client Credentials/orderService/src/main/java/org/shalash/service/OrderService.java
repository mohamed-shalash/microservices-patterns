package org.shalash.service;


import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {


    public String getOrders() {

        try {
            String token = callPayment();
            return token;
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    String callPayment() {

        String token = generateToken();

        log.info(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String response = new RestTemplate().exchange(
                "http://localhost:8082/payment/all",
                HttpMethod.GET,
                entity,
                String.class
        ).getBody();

        return response;
    }

    public String generateToken() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/auth/login?username=admin&password=1234";

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, null, Map.class);

        return (String) response.getBody().get("access_token");
    }


}
