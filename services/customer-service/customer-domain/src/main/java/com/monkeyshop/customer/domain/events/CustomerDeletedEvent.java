package com.monkeyshop.customer.domain.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class CustomerDeletedEvent extends Event {

    public CustomerDeletedEvent(String customerId, String author) {
        super(customerId, "deleted", author);
    }
}
