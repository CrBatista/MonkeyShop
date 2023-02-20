package com.monkeyshop.auth.domain.events;

import com.monkeyshop.security.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreatedEvent extends UserEvent {

    @Field("data.username")
    private String username;

    @Field("data.email")
    private String email;

    @Field("data.passwordHash")
    private String passwordHash;

    @Field("data.role")
    private String role;

    @JsonCreator
    public UserCreatedEvent() {
    }

    public UserCreatedEvent(String author, String username, String email, String passwordHash) {
        super("created", author);
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = Role.USER.getRole();
    }
}
