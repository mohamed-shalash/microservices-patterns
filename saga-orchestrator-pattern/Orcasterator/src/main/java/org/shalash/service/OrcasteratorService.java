package org.shalash.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class OrcasteratorService {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final OrderService orderService;

    public String order(String orderId) {

        System.out.println("Saga Started for order: " + orderId);

        boolean paymentSuccess = paymentService.processPayment(orderId);

        if (!paymentSuccess) {
            orderService.cancelOrder(orderId);
            return "failed to process payment";
        }

        boolean stockReserved = inventoryService.reserveStock(orderId);

        if (!stockReserved) {
            paymentService.refundPayment(orderId);
            orderService.cancelOrder(orderId);
            return "failed to reserve stock";
        }

        orderService.completeOrder(orderId);
        return "Saga Completed for order: " + orderId;
    }
}
