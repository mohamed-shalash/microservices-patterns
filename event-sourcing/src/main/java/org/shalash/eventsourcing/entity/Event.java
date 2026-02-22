package org.shalash.eventsourcing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "event")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private EventType type;
    private String data;
    private double amount;
}
