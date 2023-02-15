package com.monkeyshop.auth.rest.delegate;

import com.monkeyshop.auth.domain.SignUp;
import com.monkeyshop.auth.rest.api.AuthApiDelegate;
import com.monkeyshop.auth.rest.model.SignUpRequest;
import com.monkeyshop.auth.rest.security.CustomPasswordEncoder;
import com.monkeyshop.auth.handler.SignUpHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthDelegate implements AuthApiDelegate {

    private final CustomPasswordEncoder customPasswordEncoder;
    private final SignUpHandler signUpUseCase;

    @Override
    public ResponseEntity<Void> signup(SignUpRequest signUpRequest) {

        SignUp signUp = SignUp.builder()
            .username(signUpRequest.getUsername())
            .passwordHash(customPasswordEncoder.encode(signUpRequest.getPassword()))
            .build();

        signUpUseCase.signUp(signUp);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}