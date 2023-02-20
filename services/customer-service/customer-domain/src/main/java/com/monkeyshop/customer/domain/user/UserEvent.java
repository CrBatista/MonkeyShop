package com.monkeyshop.customer.domain.user;

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
@Document(collection = "user_events")
public class UserEvent {

    @Field("userId")
    private String userId;
    private String type;
    private Instant timestamp;
    private String author;

    public UserEvent(String type, String author) {
        this.userId = UUID.randomUUID().toString();
        this.type = type;
        this.timestamp = Instant.now();
        this.author = author;
    }

    public UserEvent(String userId, String type, String author) {
        this.userId = userId;
        this.type = type;
        this.timestamp = Instant.now();
        this.author = author;
    }
}
