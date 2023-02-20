package com.monkeyshop.customer.handler;

import com.monkeyshop.customer.domain.user.UserCreatedEvent;
import com.monkeyshop.customer.domain.user.UserDeletedEvent;
import com.monkeyshop.customer.domain.user.UserUpdatedEvent;
import com.monkeyshop.customer.persistence.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserHandler {

    private final UserCommandRepository userCommandRepository;

    public void create(UserCreatedEvent userCreatedEvent) throws ResponseStatusException {
      userCommandRepository.save(userCreatedEvent);
    }

    public void update(UserUpdatedEvent userUpdatedEvent) throws ResponseStatusException {
      userCommandRepository.update(userUpdatedEvent);
    }

    public void delete(UserDeletedEvent userDeletedEvent) throws ResponseStatusException {
      userCommandRepository.delete(userDeletedEvent);
    }
}
