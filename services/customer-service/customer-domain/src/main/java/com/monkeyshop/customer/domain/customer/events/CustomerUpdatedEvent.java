package com.monkeyshop.customer.domain.customer.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document
public class CustomerUpdatedEvent extends CustomerEvent {

    @Field("data.name")
    private String name;

    @Field("data.surname")
    private String surname;

    @Field("data.photoUrl")
    private String photoUrl;

    public CustomerUpdatedEvent(String customerId, String author, String name, String surname, String photoUrl) {
        super(customerId, "updated", author);
        this.name = name;
        this.surname = surname;
        this.photoUrl = photoUrl;
    }
}
