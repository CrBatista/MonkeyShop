package com.monkeyshop.auth.handler;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.domain.events.UserDeletedEvent;
import com.monkeyshop.auth.domain.events.UserUpdatedEvent;
import com.monkeyshop.auth.domain.user.UserAggregate;
import com.monkeyshop.auth.persistence.UserCommandRepository;
import com.monkeyshop.auth.persistence.UserQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserHandlerTest {

    @Mock
    private UserCommandRepository userCommandRepository;

    @Mock
    private UserQueryRepository userQueryRepository;

    @InjectMocks
    private UserHandler userHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        UserAggregate user = mock(UserAggregate.class);
        when(userQueryRepository.findAll()).thenReturn(Collections.singletonList(user));

        assertEquals(Collections.singletonList(user), userHandler.findAll());
        verify(userQueryRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        UserAggregate user = mock(UserAggregate.class);
        when(userQueryRepository.findById("userId")).thenReturn(Optional.of(user));

        assertEquals(Optional.of(user), userHandler.findById("userId"));
        verify(userQueryRepository, times(1)).findById("userId");
    }

    @Test
    public void testCreate() {
        UserCreatedEvent userCreatedEvent = mock(UserCreatedEvent.class);
        when(userCreatedEvent.getUserId()).thenReturn("userId");
        when(userCreatedEvent.getUsername()).thenReturn("test");

        when(userQueryRepository.findByUsernameIgnoreCase("test")).thenReturn(Optional.empty());
        when(userCommandRepository.save(any(UserCreatedEvent.class))).thenReturn(userCreatedEvent);

        assertEquals("userId", userHandler.create(userCreatedEvent));
        verify(userQueryRepository, times(1)).findByUsernameIgnoreCase("test");
        verify(userCommandRepository, times(1)).save(userCreatedEvent);
    }

    @Test
    public void testCreateWithExistingUsername() {
        UserCreatedEvent userCreatedEvent = mock(UserCreatedEvent.class);
        when(userCreatedEvent.getUsername()).thenReturn("test");

        when(userQueryRepository.findByUsernameIgnoreCase("test")).thenReturn(Optional.of(mock(UserAggregate.class)));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userHandler.create(userCreatedEvent);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Username test already in use", exception.getReason());
        verify(userQueryRepository, times(1)).findByUsernameIgnoreCase("test");
        verify(userCommandRepository, times(0)).save(userCreatedEvent);
    }

    @Test
    public void testUpdate() {
        UserUpdatedEvent userUpdatedEvent = mock(UserUpdatedEvent.class);
        when(userUpdatedEvent.getUserId()).thenReturn("userId");

        when(userQueryRepository.findById("userId")).thenReturn(Optional.of(mock(UserAggregate.class)));

        userHandler.update(userUpdatedEvent);
        verify(userQueryRepository, times(1)).findById("userId");
        verify(userCommandRepository, times(1)).update(userUpdatedEvent);
    }

    @Test
    public void delete_withValidUserId_deletesUser() {
        String userId = "userId";
        String author = "userId";
        UserDeletedEvent userDeletedEvent = new UserDeletedEvent(userId, author);
        when(userQueryRepository.findById(userId)).thenReturn(Optional.of(mock(UserAggregate.class)));

        userHandler.delete(userDeletedEvent);

        verify(userQueryRepository, times(1)).findById(userId);
        verify(userCommandRepository, times(1)).delete(userDeletedEvent);
    }

    @Test
    public void delete_withInvalidUserId_throwsResponseStatusException() {
        String userId = "userId";
        String author = "author";

        UserDeletedEvent userDeletedEvent = new UserDeletedEvent(userId, author);
        when(userQueryRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            userHandler.delete(userDeletedEvent));

        String expectedMessage = String.format("400 BAD_REQUEST \"Username with ID %s does not exists\"", userId);
        assertEquals(expectedMessage, exception.getMessage());
    }
}