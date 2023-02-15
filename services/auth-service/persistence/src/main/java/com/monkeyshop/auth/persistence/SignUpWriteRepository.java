package com.monkeyshop.auth.persistence;

import com.monkeyshop.auth.domain.SignUp;
import com.monkeyshop.auth.mongo.repositories.SignUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpWriteRepository {

    private final SignUpRepository signUpRepository;

    public SignUp save(SignUp signUp) {
        return signUpRepository.save(signUp);
    }

}
