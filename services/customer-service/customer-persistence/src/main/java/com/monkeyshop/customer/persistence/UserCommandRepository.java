package com.monkeyshop.customer.persistence;

import com.monkeyshop.customer.domain.repository.UserEventCommandRepository;
import com.monkeyshop.customer.domain.user.UserDeletedEvent;
import com.monkeyshop.customer.domain.user.UserEvent;
import com.monkeyshop.customer.domain.user.UserUpdatedEvent;
import com.monkeyshop.customer.mongo.repositories.UserEventCommandMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCommandRepository implements UserEventCommandRepository {

    private final UserEventCommandMongoRepository userEventCommandMongoRepository;

    public void update(UserUpdatedEvent userUpdatedEvent) {
        save(userUpdatedEvent);
    }

    public void delete(UserDeletedEvent userDeletedEvent) {
        save(userDeletedEvent);
    }

    @Override
    public UserEvent save(UserEvent userEvent) {
        return userEventCommandMongoRepository.save(userEvent);
    }
}
