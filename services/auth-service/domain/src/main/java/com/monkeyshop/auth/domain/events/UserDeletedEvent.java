package com.monkeyshop.auth.domain.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "UserEvents")
public class UserDeletedEvent extends Event {

    private String userId;

    public UserDeletedEvent(String createdBy, String userId) {
        super("deleted", createdBy);
        this.userId = userId;
    }
}
