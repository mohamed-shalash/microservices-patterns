package org.shalash.repository;

import org.shalash.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCommandRepository extends JpaRepository<Order, Long> {
}