package com.monkeyshop.customer.domain.repository;

import com.monkeyshop.customer.domain.customer.CustomerAggregate;
import com.monkeyshop.customer.domain.customer.events.CustomerCreatedEvent;
import com.monkeyshop.customer.domain.customer.events.CustomerEvent;

import java.util.List;
import java.util.Optional;

public interface CustomerAggregateQueryRepository {

  List<CustomerAggregate> findAll();

  Optional<CustomerAggregate> findById(String id);
}
