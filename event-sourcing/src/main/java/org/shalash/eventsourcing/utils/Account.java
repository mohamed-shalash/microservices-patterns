package org.shalash.eventsourcing.utils;

import lombok.Data;
import org.shalash.eventsourcing.entity.Event;
import org.shalash.eventsourcing.entity.EventType;

import java.util.List;

@Data
public class Account {
    private String accountId;
    private double balance = 0;

    public Account(List<Event> events) {
        for (Event e : events) {
            switch (e.getType()) {
                case ACCOUNT_CREATED -> this.accountId = e.getData();
                case DEPOSIT -> this.balance += e.getAmount();
                case WITHDRAWAL -> this.balance -= e.getAmount();
            }
        }
    }

}
