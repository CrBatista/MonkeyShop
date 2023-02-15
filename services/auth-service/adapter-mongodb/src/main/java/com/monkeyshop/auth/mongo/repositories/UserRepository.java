package com.monkeyshop.auth.mongo.repositories;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserCreatedEvent, String> {

    Optional<UserCreatedEvent> findByUsernameIgnoreCase(String username);

}
