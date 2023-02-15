package com.monkeyshop.auth.mongo.repositories;

import com.monkeyshop.auth.domain.SignUp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SignUpRepository extends MongoRepository<SignUp, String> {

    Optional<SignUp> findByUsernameIgnoreCase(String username);

}
