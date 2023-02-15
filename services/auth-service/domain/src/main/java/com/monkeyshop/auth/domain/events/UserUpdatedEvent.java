package com.monkeyshop.auth.domain.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Setter
@Getter
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
