package com.monkeyshop.auth.domain.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class UserDeletedEvent extends UserEvent {

    public UserDeletedEvent(String userId, String author) {
        super(userId, "deleted", author);
    }
}
