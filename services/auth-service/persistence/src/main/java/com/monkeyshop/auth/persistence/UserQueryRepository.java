package com.monkeyshop.auth.persistence;

import com.monkeyshop.auth.domain.user.UserAggregate;
import com.monkeyshop.auth.mongo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserQueryRepository {

    private final UserRepository userRepository;

    public List<UserAggregate> findAll() {
        return userRepository.findAll();
    }

    public Optional<UserAggregate> findByUsernameIgnoreCase(String id) {
        return userRepository.findByUsernameIgnoreCase(id);
    }

    public Optional<UserAggregate> findById(String id) {
        return userRepository.findById(id);
    }

}
