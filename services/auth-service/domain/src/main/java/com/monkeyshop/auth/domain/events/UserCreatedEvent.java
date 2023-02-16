package com.monkeyshop.auth.domain.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document
public class UserCreatedEvent extends Event {

    @Field("data.username")
    private String username;

    @Field("data.email")
    private String email;

    @Field("data.passwordHash")
    private String passwordHash;

    public UserCreatedEvent(String author, String username, String email, String passwordHash) {
        super("created", author);
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }
}
