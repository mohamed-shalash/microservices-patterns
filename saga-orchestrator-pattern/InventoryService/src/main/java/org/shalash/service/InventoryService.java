package org.shalash.service;

import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    public boolean reserveStock(String orderId) {
        System.out.println("Reserving stock...");
        return Math.random() > 0.5;
    }
}
