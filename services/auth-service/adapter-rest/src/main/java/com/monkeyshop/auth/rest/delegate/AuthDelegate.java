package com.monkeyshop.auth.rest.delegate;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.rest.api.AuthApiDelegate;
import com.monkeyshop.auth.rest.model.SignUpRequest;
import com.monkeyshop.auth.rest.security.CustomPasswordEncoder;
import com.monkeyshop.auth.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthDelegate implements AuthApiDelegate {

    private final CustomPasswordEncoder customPasswordEncoder;
    private final UserHandler signUpUseCase;

    @Override
    public ResponseEntity<Void> signup(SignUpRequest signUpRequest) {

        UserCreatedEvent userCreatedEvent = UserCreatedEvent.builder()
            .username(signUpRequest.getUsername())
            .passwordHash(customPasswordEncoder.encode(signUpRequest.getPassword()))
            .build();

        signUpUseCase.signUp(userCreatedEvent);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}