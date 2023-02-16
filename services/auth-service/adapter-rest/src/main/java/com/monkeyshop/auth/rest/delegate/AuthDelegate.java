package com.monkeyshop.auth.rest.delegate;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.domain.events.UserDeletedEvent;
import com.monkeyshop.auth.domain.events.UserUpdatedEvent;
import com.monkeyshop.auth.rest.model.CreateUserRequest;
import com.monkeyshop.auth.rest.model.UpdateUserRequest;
import com.monkeyshop.auth.rest.api.AuthApiDelegate;
import com.monkeyshop.auth.rest.model.User;
import com.monkeyshop.auth.rest.security.CustomPasswordEncoder;
import com.monkeyshop.auth.handler.UserHandler;
import com.monkeyshop.security.AuthenticationFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthDelegate implements AuthApiDelegate {

    private final CustomPasswordEncoder customPasswordEncoder;
    private final UserHandler userHandler;

    @Override
    public ResponseEntity<Void> createUser(CreateUserRequest createUserRequest) {
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(
            new AuthenticationFetcher().getLoggedUserID(),
            createUserRequest.getUsername(),
            createUserRequest.getEmail(),
            customPasswordEncoder.encode(createUserRequest.getPassword()));

        userHandler.create(userCreatedEvent);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<User> updateUser(String userId, UpdateUserRequest updateUserRequest) {
        UserUpdatedEvent userUpdatedEvent = new UserUpdatedEvent(
            userId,
            new AuthenticationFetcher().getLoggedUserID(),
            updateUserRequest.getUsername(),
            updateUserRequest.getEmail(),
            Optional.ofNullable(updateUserRequest.getPassword())
                .map(customPasswordEncoder::encode)
                .orElse(null));

        userHandler.update(userUpdatedEvent);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<User> deleteUser(String userId) {
        UserDeletedEvent userDeletedEvent = new UserDeletedEvent(
            userId,
            new AuthenticationFetcher().getLoggedUserID());

        userHandler.delete(userDeletedEvent);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
