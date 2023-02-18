package com.monkeyshop.customer.domain.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document
public class CustomerCreatedEvent extends Event {

    @Field("data.name")
    private String name;

    @Field("data.surname")
    private String surname;

    public CustomerCreatedEvent(String author, String name, String surname) {
        super("created", author);
        this.name = name;
        this.surname = surname;
    }
}
