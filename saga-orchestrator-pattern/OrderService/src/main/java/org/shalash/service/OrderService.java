package org.shalash.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public void completeOrder(String orderId) {
        System.out.println("Order Completed!");
    }

    public void cancelOrder(String orderId) {
        System.out.println("Order Cancelled!");
    }
}
