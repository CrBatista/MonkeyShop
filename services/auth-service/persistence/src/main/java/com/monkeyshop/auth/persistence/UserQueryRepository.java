package com.monkeyshop.auth.persistence;

import com.monkeyshop.auth.domain.user.User;
import com.monkeyshop.auth.mongo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserQueryRepository {

    private final UserRepository userRepository;

    public Optional<User> findByUsernameIgnoreCase(String id) {
        return userRepository.findByUsernameIgnoreCase(id);
    }

}
