package com.monkeyshop.customer.domain.events;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer_events")
public class Event {

    @Field("customerId")
    private String customerId;
    private String type;
    private Instant timestamp;
    private String author;

    public Event(String type, String author) {
        this.customerId = UUID.randomUUID().toString();
        this.type = type;
        this.timestamp = Instant.now();
        this.author = author;
    }

    public Event(String userId, String type, String author) {
        this.customerId = userId;
        this.type = type;
        this.timestamp = Instant.now();
        this.author = author;
    }
}
