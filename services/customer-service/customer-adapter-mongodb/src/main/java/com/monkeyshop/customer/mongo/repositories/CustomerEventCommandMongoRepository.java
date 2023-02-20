package com.monkeyshop.customer.mongo.repositories;

import com.monkeyshop.customer.domain.customer.events.CustomerEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerEventCommandMongoRepository extends MongoRepository<CustomerEvent, String> {

}
