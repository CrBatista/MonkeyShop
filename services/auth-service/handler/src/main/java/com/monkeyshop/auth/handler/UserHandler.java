package com.monkeyshop.auth.handler;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
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

    public void signUp(UserCreatedEvent userCreatedEvent) throws ResponseStatusException {
        throwIfFound(userCreatedEvent);
        userCommandRepository.save(userCreatedEvent);
    }

    private void throwIfFound(UserCreatedEvent userCreatedEvent) {
        userQueryRepository.findByUsernameIgnoreCase(userCreatedEvent.getUsername())
            .ifPresent(conflictingSignUp -> {
                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Username %s already in use", conflictingSignUp.getUsername()));
            });
    }
}
