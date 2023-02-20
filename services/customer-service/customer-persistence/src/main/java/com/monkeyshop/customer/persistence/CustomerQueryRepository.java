package com.monkeyshop.customer.persistence;

import com.monkeyshop.customer.domain.customer.CustomerAggregate;
import com.monkeyshop.customer.domain.repository.CustomerAggregateQueryRepository;
import com.monkeyshop.customer.mongo.repositories.CustomerQueryMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerQueryRepository implements CustomerAggregateQueryRepository {

    private final CustomerQueryMongoRepository customerQueryMongoRepository;

    @Override
    public List<CustomerAggregate> findAll() {
        return customerQueryMongoRepository.findAll();
    }

    @Override
    public Optional<CustomerAggregate> findById(String id) {
        return customerQueryMongoRepository.findById(id);
    }
}
