package com.monkeyshop.auth.handler;

import com.monkeyshop.auth.domain.events.UserEvent;
import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.domain.events.UserDeletedEvent;
import com.monkeyshop.auth.domain.events.UserUpdatedEvent;
import com.monkeyshop.auth.domain.user.UserAggregate;
import com.monkeyshop.auth.kafka.producer.KafkaProducerService;
import com.monkeyshop.auth.persistence.UserCommandRepository;
import com.monkeyshop.auth.persistence.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserHandler {

    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;
    private final KafkaProducerService kafkaProducerService;

    public List<UserAggregate> findAll() {
        return userQueryRepository.findAll();
    }

    public Optional<UserAggregate> findById(String userId) {
        return userQueryRepository.findById(userId);
    }

    @Transactional
    public String create(UserCreatedEvent userCreatedEvent) throws ResponseStatusException {
        throwIfFound(userCreatedEvent);
        String userId = userCommandRepository.save(userCreatedEvent).getUserId();
        kafkaProducerService.sendMessage(userCreatedEvent);
        return userId;
    }

    @Transactional
    public void update(UserUpdatedEvent userUpdatedEvent) throws ResponseStatusException {
        throwIfEmpty(userUpdatedEvent);
        userCommandRepository.update(userUpdatedEvent);
        kafkaProducerService.sendMessage(userUpdatedEvent);
    }

    @Transactional
    public void delete(UserDeletedEvent userDeletedEvent) throws ResponseStatusException {
        throwIfEmpty(userDeletedEvent);
        userCommandRepository.delete(userDeletedEvent);
        kafkaProducerService.sendMessage(userDeletedEvent);
    }

    private void throwIfFound(UserCreatedEvent userCreatedEvent) {
        userQueryRepository.findByUsernameIgnoreCase(userCreatedEvent.getUsername())
            .ifPresent(conflictingSignUp -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Username %s already in use", userCreatedEvent.getUsername()));
            });
    }

    private void throwIfEmpty(UserEvent userEvent) {
        userQueryRepository.findById(userEvent.getUserId())
            .orElseThrow(() -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Username with ID %s does not exists", userEvent.getUserId()));
            });
    }
}
