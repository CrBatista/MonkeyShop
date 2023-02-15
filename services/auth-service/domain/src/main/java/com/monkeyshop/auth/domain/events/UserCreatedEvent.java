package com.monkeyshop.auth.domain.events;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCreatedEvent extends Event {

    private String username;
    private String passwordHash;

    public UserCreatedEvent(String createdBy, String username, String passwordHash) {
        super("created", createdBy);
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
