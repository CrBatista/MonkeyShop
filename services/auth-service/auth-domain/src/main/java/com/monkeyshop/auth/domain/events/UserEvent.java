package com.monkeyshop.auth.domain.events;

import lombok.*;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "user_events")
public class UserEvent {

    @Field("userId")
    private String userId;
    private String type;
    private Instant timestamp;
    private String author;

    public UserEvent(String type, String author) {
        this.type = type;
        this.author = author;
    }

    @JsonCreator
    public UserEvent() {
    }

    public UserEvent(String userId, String type, String author) {
        this.userId = userId;
        this.type = type;
        this.author = author;
    }
}
