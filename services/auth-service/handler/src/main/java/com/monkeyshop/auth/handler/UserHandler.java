package com.monkeyshop.auth.handler;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.domain.events.UserUpdatedEvent;
import com.monkeyshop.auth.persistence.UserCommandRepository;
import com.monkeyshop.auth.persistence.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserHandler {

    @Autowired
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;

    public void create(UserCreatedEvent userCreatedEvent) throws ResponseStatusException {
        throwIfFound(userCreatedEvent);
        userCommandRepository.save(userCreatedEvent);
    }

    public void update(UserUpdatedEvent userUpdatedEvent) throws ResponseStatusException {
        throwIfEmpty(userUpdatedEvent);
        userCommandRepository.update(userUpdatedEvent);
    }

    private void throwIfFound(UserCreatedEvent userCreatedEvent) {
        userQueryRepository.findByUsernameIgnoreCase(userCreatedEvent.getUsername())
            .ifPresent(conflictingSignUp -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Username %s already in use", conflictingSignUp.getUsername()));
            });
    }

    private void throwIfEmpty(UserUpdatedEvent userUpdatedEvent) {
        userQueryRepository.findById(userUpdatedEvent.getUserId())
            .orElseThrow(() -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Username with ID %s does not exists", userUpdatedEvent.getUserId()));
            });
    }
}
