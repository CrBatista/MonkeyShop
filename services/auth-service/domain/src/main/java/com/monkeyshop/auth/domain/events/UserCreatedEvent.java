package com.monkeyshop.auth.domain.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Builder
@Document(collection = "UserEvents")
public class UserCreatedEvent extends Event {

    private String username;
    private String passwordHash;

    public UserCreatedEvent(String createdBy, String username, String passwordHash) {
        super("created", createdBy);
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
