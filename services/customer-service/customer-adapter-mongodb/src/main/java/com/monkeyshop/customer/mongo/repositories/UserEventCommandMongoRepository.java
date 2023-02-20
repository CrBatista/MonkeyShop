package com.monkeyshop.customer.mongo.repositories;

import com.monkeyshop.customer.domain.user.UserEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEventCommandMongoRepository extends MongoRepository<UserEvent, String> {

}
