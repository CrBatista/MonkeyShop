package com.monkeyshop.customer.persistence;

import com.monkeyshop.customer.domain.customer.CustomerAggregate;
import com.monkeyshop.customer.mongo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerQueryRepository {

    private final CustomerRepository customerRepository;

    public List<CustomerAggregate> findAll() {
        return customerRepository.findAll();
    }

    public Optional<CustomerAggregate> findById(String id) {
        return customerRepository.findById(id);
    }

}
