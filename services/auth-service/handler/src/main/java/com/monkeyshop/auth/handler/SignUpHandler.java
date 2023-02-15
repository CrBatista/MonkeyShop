package com.monkeyshop.auth.handler;

import com.monkeyshop.auth.domain.SignUp;
import com.monkeyshop.auth.persistence.SignUpReadRepository;
import com.monkeyshop.auth.persistence.SignUpWriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SignUpHandler {

    private final SignUpWriteRepository signUpWriteRepository;
    private final SignUpReadRepository signUpReadRepository;

    public void signUp(SignUp signUp) throws ResponseStatusException {
        throwIfFound(signUp);
        signUpWriteRepository.save(signUp);
    }

    private void throwIfFound(SignUp signUp) {
        signUpReadRepository.findByUsernameIgnoreCase(signUp.getUsername())
            .ifPresent(conflictingSignUp -> {
                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Username %s already in use", conflictingSignUp.getUsername()));
            });
    }
}
