package com.monkeyshop.auth.persistence;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.mongo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SignUpReadRepository {

    private final UserRepository userRepository;

    public Optional<UserCreatedEvent> findByUsernameIgnoreCase(String id) {
        return userRepository.findByUsernameIgnoreCase(id);
    }

}
