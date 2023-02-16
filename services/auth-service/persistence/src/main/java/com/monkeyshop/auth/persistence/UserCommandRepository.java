package com.monkeyshop.auth.persistence;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.domain.events.UserDeletedEvent;
import com.monkeyshop.auth.domain.events.UserUpdatedEvent;
import com.monkeyshop.auth.mongo.repositories.UserEventRepository;
import com.monkeyshop.auth.mongo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCommandRepository {

    private final UserEventRepository userEventRepository;

    public UserCreatedEvent save(UserCreatedEvent userCreatedEvent) {
        return userEventRepository.save(userCreatedEvent);
    }

    public UserUpdatedEvent update(UserUpdatedEvent userUpdatedEvent) {
        return userEventRepository.save(userUpdatedEvent);
    }

    public UserDeletedEvent delete(UserDeletedEvent userDeletedEvent) {
        return userEventRepository.save(userDeletedEvent);
    }
}
