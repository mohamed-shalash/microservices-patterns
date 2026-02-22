package org.shalash.eventsourcing.service;

import lombok.RequiredArgsConstructor;
import org.shalash.eventsourcing.entity.Event;
import org.shalash.eventsourcing.entity.EventType;
import org.shalash.eventsourcing.repository.EventRepository;
import org.shalash.eventsourcing.utils.Account;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final EventRepository eventRepository;


    public void createAccount(String accountId) {
        Event event = new Event(UUID.randomUUID().toString(), EventType.ACCOUNT_CREATED, accountId, 0);
        eventRepository.save(event);
    }

    public void deposit(String accountId, double amount) {
        Event event = new Event(UUID.randomUUID().toString(), EventType.DEPOSIT, accountId, amount);
        eventRepository.save(event);
    }

    public void withdraw(String accountId, double amount) {
        Event event = new Event(UUID.randomUUID().toString(), EventType.WITHDRAWAL, accountId, amount);
        eventRepository.save(event);
    }

    public double getBalance(String accountId) {
        List<Event> events = eventRepository.findByData(accountId);
        Account account = new Account(events);
        return account.getBalance();
    }
}
