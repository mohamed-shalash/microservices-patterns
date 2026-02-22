package org.shalash.eventsourcing.repository;

import org.shalash.eventsourcing.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    List<Event> findByData(String accountId);
}
