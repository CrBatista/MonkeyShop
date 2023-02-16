package com.monkeyshop.auth.persistence;

import com.monkeyshop.auth.domain.user.User;
import com.monkeyshop.auth.mongo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserQueryRepository {

    private final UserRepository userRepository;

    public Optional<User> findByUsernameIgnoreCase(String id) {
        return userRepository.findByUsernameIgnoreCase(id);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

}
