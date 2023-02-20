package com.monkeyshop.customer.domain.user;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document
public class UserUpdatedEvent extends UserEvent {

    @Field("data.username")
    private String username;

    @Field("data.email")
    private String email;

    @Field("data.passwordHash")
    private String passwordHash;

    @Field("data.role")
    private String role;

    public UserUpdatedEvent() {
        super(null, "updated", null);
    }

    @JsonCreator
    public UserUpdatedEvent(@JsonProperty("author") String userId,
        @JsonProperty("author") String author,
        @JsonProperty("username") String username,
        @JsonProperty("email") String email,
        @JsonProperty("email") String role,
        @JsonProperty("passwordHash") String passwordHash) {
        super(userId, "updated", author);
        this.username = username;
        this.email = email;
        this.role = role;
        this.passwordHash = passwordHash;
    }
}
