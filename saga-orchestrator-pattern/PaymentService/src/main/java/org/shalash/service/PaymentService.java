package org.shalash.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean processPayment(String orderId) {
        System.out.println("Processing payment...");
        return Math.random() > 0.5;
    }

    public void refundPayment(String orderId) {
        System.out.println("Refund payment for " + orderId);
    }
}
