package com.monkeyshop.customer.domain.customer.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class CustomerDeletedEvent extends CustomerEvent {

    public CustomerDeletedEvent(String customerId, String author) {
        super(customerId, "deleted", author);
    }
}
