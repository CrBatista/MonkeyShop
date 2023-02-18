package com.monkeyshop.customer.mongo.repositories;

import com.monkeyshop.customer.domain.events.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerEventRepository extends MongoRepository<Event, UUID> {

}
