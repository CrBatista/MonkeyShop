package com.monkeyshop.auth.mongo.repositories;

import com.monkeyshop.auth.domain.user.UserAggregate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserAggregate, String> {

    Optional<UserAggregate> findByUsernameIgnoreCase(String username);
}
