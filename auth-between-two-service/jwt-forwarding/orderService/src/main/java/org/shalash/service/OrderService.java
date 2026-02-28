package org.shalash.service;


import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
@RequiredArgsConstructor
public class OrderService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpServletRequest request;

     void callPayment() {
        System.out.println("you are admin but are you allowed to call payment?");

         String authHeader = request.getHeader("Authorization");

         HttpHeaders headers = new HttpHeaders();
         headers.set("Authorization", authHeader);

         HttpEntity<String> entity = new HttpEntity<>(headers);

        String response = restTemplate.exchange(
                "http://localhost:8082/payment/all",
                HttpMethod.GET,
                entity,
                String.class
        ).getBody();

        System.out.println(response);

    }
    public String getOrders() {
         try {
              callPayment();
         }catch (Exception e) {
             return "You are not superadmin";
         }


        return "Orders visible only with valid JWT";
    }
}
