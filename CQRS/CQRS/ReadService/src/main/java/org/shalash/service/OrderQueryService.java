package org.shalash.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.shalash.entity.Order;
import org.shalash.repository.OrderQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderQueryRepository repository;

    @Transactional
    public List<Order> getAllOrders() {
        return repository.findAll();
    }


}
