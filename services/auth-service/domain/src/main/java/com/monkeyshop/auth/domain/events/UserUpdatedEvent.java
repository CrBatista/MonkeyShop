package com.monkeyshop.auth.domain.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Setter
@Getter
@Document
public class UserUpdatedEvent extends Event {

    @Field("data.username")
    private String username;

    @Field("data.email")
    private String email;

    @Field("data.passwordHash")
    private String passwordHash;

    @Field("data.role")
    private String role;

    public UserUpdatedEvent(String userId, String author, String username, String email, String role, String passwordHash) {
        super(userId, "updated", author);
        this.username = username;
        this.email = email;
        this.role = role;
        this.passwordHash = passwordHash;
    }
}
