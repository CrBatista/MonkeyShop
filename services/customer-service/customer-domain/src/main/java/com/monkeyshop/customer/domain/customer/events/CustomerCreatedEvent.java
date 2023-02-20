package com.monkeyshop.customer.domain.customer.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document
public class CustomerCreatedEvent extends CustomerEvent {

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
