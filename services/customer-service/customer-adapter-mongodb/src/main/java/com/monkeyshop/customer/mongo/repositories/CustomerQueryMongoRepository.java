package com.monkeyshop.customer.mongo.repositories;

import com.monkeyshop.customer.domain.customer.CustomerAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerQueryMongoRepository extends MongoRepository<CustomerAggregate, String> {

}
