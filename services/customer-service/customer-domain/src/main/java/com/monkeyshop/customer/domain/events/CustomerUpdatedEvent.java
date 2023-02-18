package com.monkeyshop.customer.domain.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document
public class CustomerUpdatedEvent extends Event {

    @Field("data.name")
    private String name;

    @Field("data.surname")
    private String surname;

    public CustomerUpdatedEvent(String userId, String author, String name, String surname) {
        super(userId, "updated", author);
        this.name = name;
        this.surname = surname;
    }
}
