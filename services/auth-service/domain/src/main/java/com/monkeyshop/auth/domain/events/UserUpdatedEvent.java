package com.monkeyshop.auth.domain.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Builder
@Document(collection = "UserEvents")
public class UserUpdatedEvent extends Event {

    private UUID userId;
    private String username;
    private String passwordHash;

    public UserUpdatedEvent(UUID userId, String createdBy, String username, String passwordHash) {
        super("updated", createdBy);
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
