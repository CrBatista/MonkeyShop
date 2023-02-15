package com.monkeyshop.auth.persistence;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.mongo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpWriteRepository {

    private final UserRepository userRepository;

    public UserCreatedEvent save(UserCreatedEvent userCreatedEvent) {
        return userRepository.save(userCreatedEvent);
    }

}
