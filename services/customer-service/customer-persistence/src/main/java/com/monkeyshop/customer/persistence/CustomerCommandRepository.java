package com.monkeyshop.customer.persistence;

import com.monkeyshop.customer.domain.events.CustomerCreatedEvent;
import com.monkeyshop.customer.domain.events.CustomerDeletedEvent;
import com.monkeyshop.customer.domain.events.CustomerUpdatedEvent;
import com.monkeyshop.customer.mongo.repositories.CustomerEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerCommandRepository {

    private final CustomerEventRepository customerEventRepository;

    public CustomerCreatedEvent save(CustomerCreatedEvent customerCreatedEvent) {
        return customerEventRepository.save(customerCreatedEvent);
    }

    public void update(CustomerUpdatedEvent customerUpdatedEvent) {
        customerEventRepository.save(customerUpdatedEvent);
    }

    public void delete(CustomerDeletedEvent customerDeletedEvent) {
        customerEventRepository.save(customerDeletedEvent);
    }
}
