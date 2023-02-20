package com.monkeyshop.auth.rest.delegate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monkeyshop.auth.rest.security.CustomPasswordEncoder;
import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.domain.events.UserDeletedEvent;
import com.monkeyshop.auth.domain.events.UserUpdatedEvent;
import com.monkeyshop.auth.rest.model.CreateUserRequest;
import com.monkeyshop.auth.rest.model.UserDetails;
import com.monkeyshop.auth.rest.model.UserHistoryDetails;
import com.monkeyshop.auth.rest.model.UpdateUserRequest;
import com.monkeyshop.auth.rest.api.AuthApiDelegate;
import com.monkeyshop.auth.rest.model.User;
import com.monkeyshop.auth.handler.UserHandler;
import com.monkeyshop.security.AuthenticationFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDelegate implements AuthApiDelegate {

    private final CustomPasswordEncoder customPasswordEncoder;
    private final UserHandler userHandler;
    private final AuthenticationFetcher authenticationFetcher;

    @Override
    public ResponseEntity<List<User>> findUsers() {
        List<User> users = userHandler.findAll().stream()
            .map(userAggregate ->
                new User()
                    .id(userAggregate.getId())
                    .email(userAggregate.getEmail())
                    .username(userAggregate.getUsername()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserDetails> findUser(String userId) {
        return userHandler.findById(userId)
            .map(userAggregate ->
                new UserDetails()
                    .id(userAggregate.getId())
                    .username(userAggregate.getUsername())
                    .role(userAggregate.getRole())
                    .email(userAggregate.getEmail())
                    .createdBy(userAggregate.getCreatedBy())
                    .createdAt(userAggregate.getCreatedAt().atOffset(ZoneOffset.ofHours(-1)))
                    .updatedBy(userAggregate.getUpdatedBy())
                    .updatedAt(userAggregate.getUpdatedAt().atOffset(ZoneOffset.ofHours(-1)))
                    .lastEvent(userAggregate.getLastEvent())
                    .history(userAggregate.getHistory().stream().map(
                        userHistoryAggregate -> new UserHistoryDetails()
                            .type(userHistoryAggregate.getType())
                            .author(userHistoryAggregate.getAuthor())
                            .timestamp(userHistoryAggregate.getTimestamp().atOffset(ZoneOffset.ofHours(-1)))
                            .email(userHistoryAggregate.getEmail())
                            .role(userHistoryAggregate.getRole())
                            .username(userHistoryAggregate.getUsername()))
                        .collect(Collectors.toList())))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<String> createUser(CreateUserRequest createUserRequest) {
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(
            authenticationFetcher.getLoggedUserID(),
            createUserRequest.getUsername(),
            createUserRequest.getEmail(),
            customPasswordEncoder.encode(createUserRequest.getPassword()));

        String userId = userHandler.create(userCreatedEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @Override
    public ResponseEntity<Void> updateUser(String userId, UpdateUserRequest updateUserRequest) {
        UserUpdatedEvent userUpdatedEvent = new UserUpdatedEvent(
            userId,
            authenticationFetcher.getLoggedUserID(),
            updateUserRequest.getUsername(),
            updateUserRequest.getEmail(),
            updateUserRequest.getRole(),
            Optional.ofNullable(updateUserRequest.getPassword())
                .map(customPasswordEncoder::encode)
                .orElse(null));

        userHandler.update(userUpdatedEvent);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String userId) {
        UserDeletedEvent userDeletedEvent = new UserDeletedEvent(
            userId,
            authenticationFetcher.getLoggedUserID());

        userHandler.delete(userDeletedEvent);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
