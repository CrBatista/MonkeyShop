package com.monkeyshop.auth.mongo.repositories;

import com.monkeyshop.auth.domain.events.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserEventRepository extends MongoRepository<Event, UUID> {

}
