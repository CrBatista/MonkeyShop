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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Validated
@RestController
@RequiredArgsConstructor
public class AuthDelegate implements AuthApiDelegate {

    private final CustomPasswordEncoder customPasswordEncoder;
    private final UserHandler userHandler;
    private final AuthenticationFetcher authenticationFetcher;

    @Override
    public ResponseEntity<Void> createUser(CreateUserRequest createUserRequest) {
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(
            authenticationFetcher.getLoggedUserID(),
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
            authenticationFetcher.getLoggedUserID(),
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
            authenticationFetcher.getLoggedUserID());

        userHandler.delete(userDeletedEvent);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
