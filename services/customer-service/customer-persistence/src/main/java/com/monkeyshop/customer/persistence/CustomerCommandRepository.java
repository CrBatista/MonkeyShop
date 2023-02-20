package com.monkeyshop.customer.persistence;

import com.monkeyshop.customer.domain.customer.events.CustomerEvent;
import com.monkeyshop.customer.domain.repository.CustomerEventCommandRepository;

import com.monkeyshop.customer.mongo.repositories.CustomerEventCommandMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerCommandRepository implements CustomerEventCommandRepository {

    private final CustomerEventCommandMongoRepository customerEventCommandMongoRepository;

    public void update(CustomerEvent customerEvent) {
        customerEventCommandMongoRepository.save(customerEvent);
    }

    public void delete(CustomerEvent customerEvent) {
        customerEventCommandMongoRepository.save(customerEvent);
    }

    @Override
    public CustomerEvent save(CustomerEvent customerEvent) {
        return customerEventCommandMongoRepository.save(customerEvent);
    }
}
