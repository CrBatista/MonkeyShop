package com.monkeyshop.auth.domain.events;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Builder
@Document("user_events")
public abstract class Event {

    @Id
    private UUID id;
    private String type;
    private Instant timestamp;
    private String createdBy;

    public Event(String type, String createdBy) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.timestamp = Instant.now();
        this.createdBy = createdBy;
    }
}
