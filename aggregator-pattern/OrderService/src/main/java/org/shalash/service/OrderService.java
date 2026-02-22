package org.shalash.service;

import org.shalash.object.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    List<Order> orders = new ArrayList<>();

    OrderService() {
        orders.add(new Order("1", new ArrayList<>(List.of(1, 2)), "1"));
        orders.add(new Order("2", new ArrayList<>(List.of(2, 3)), "2"));
        orders.add(new Order("3", new ArrayList<>(List.of(3)), "3"));
    }

    public Order getOrder(String id) {
        return orders.stream().filter(order -> order.getUserId().equals(id)).findFirst().orElse(null);
    }

}
