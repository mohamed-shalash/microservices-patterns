package org.shalash.outbox_pattern.repository;

import org.shalash.outbox_pattern.entity.Order;
import org.shalash.outbox_pattern.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutBoxRepository extends JpaRepository<OutboxEvent, Long> {


    List<OutboxEvent> findByProcessedFalse();
}
