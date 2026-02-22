package org.shalash.outbox_pattern.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "outbox_event")
@AllArgsConstructor
@NoArgsConstructor
public class OutboxEvent {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private int aggregateId;
    private String eventType;
    private String payload;
    private boolean processed;
}
