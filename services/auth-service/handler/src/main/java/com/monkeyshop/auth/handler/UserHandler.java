package com.monkeyshop.auth.handler;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.persistence.SignUpReadRepository;
import com.monkeyshop.auth.persistence.SignUpWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserHandler {

    @Autowired
    private final SignUpWriteRepository signUpWriteRepository;
    private final SignUpReadRepository signUpReadRepository;

    public void signUp(UserCreatedEvent userCreatedEvent) throws ResponseStatusException {
        throwIfFound(userCreatedEvent);
        signUpWriteRepository.save(userCreatedEvent);
    }

    private void throwIfFound(UserCreatedEvent userCreatedEvent) {
        signUpReadRepository.findByUsernameIgnoreCase(userCreatedEvent.getUsername())
            .ifPresent(conflictingSignUp -> {
                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Username %s already in use", conflictingSignUp.getUsername()));
            });
    }
}
