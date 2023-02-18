package com.monkeyshop.auth.persistence;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.domain.events.UserDeletedEvent;
import com.monkeyshop.auth.domain.events.UserUpdatedEvent;
import com.monkeyshop.auth.mongo.repositories.UserEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCommandRepository {

    private final UserEventRepository userEventRepository;

    public UserCreatedEvent save(UserCreatedEvent customerCreatedEvent) {
        return userEventRepository.save(customerCreatedEvent);
    }

    public void update(UserUpdatedEvent userUpdatedEvent) {
        userEventRepository.save(userUpdatedEvent);
    }

    public void delete(UserDeletedEvent userDeletedEvent) {
        userEventRepository.save(userDeletedEvent);
    }
}
