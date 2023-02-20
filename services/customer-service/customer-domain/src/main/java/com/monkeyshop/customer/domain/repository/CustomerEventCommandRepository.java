package com.monkeyshop.customer.domain.repository;

import com.monkeyshop.customer.domain.customer.events.CustomerEvent;

public interface CustomerEventCommandRepository {

  CustomerEvent save(CustomerEvent customerEvent);

}
