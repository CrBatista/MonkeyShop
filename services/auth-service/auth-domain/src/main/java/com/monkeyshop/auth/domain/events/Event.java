package com.monkeyshop.auth.domain.events;

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
@Document(collection = "user_events")
public class Event {

    @Field("userId")
    private String userId;
    private String type;
    private Instant timestamp;
    private String author;

    public Event(String type, String author) {
        this.userId = UUID.randomUUID().toString();
        this.type = type;
        this.timestamp = Instant.now();
        this.author = author;
    }

    public Event(String userId, String type, String author) {
        this.userId = userId;
        this.type = type;
        this.timestamp = Instant.now();
        this.author = author;
    }
}
