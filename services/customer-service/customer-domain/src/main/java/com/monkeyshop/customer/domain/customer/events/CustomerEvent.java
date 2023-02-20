package com.monkeyshop.customer.domain.customer.events;

import lombok.*;
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
public class CustomerEvent {

    @Field("customerId")
    private String customerId;
    private String type;
    private Instant timestamp;
    private String author;

    public CustomerEvent(String type, String author) {
        this.customerId = UUID.randomUUID().toString();
        this.type = type;
        this.timestamp = Instant.now();
        this.author = author;
    }

    public CustomerEvent(String customerId, String type, String author) {
        this.customerId = customerId;
        this.type = type;
        this.timestamp = Instant.now();
        this.author = author;
    }
}
