package com.monkeyshop.auth.rest.delegate;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.domain.events.UserDeletedEvent;
import com.monkeyshop.auth.domain.events.UserUpdatedEvent;
import com.monkeyshop.auth.handler.UserHandler;
import com.monkeyshop.auth.rest.model.CreateUserRequest;
import com.monkeyshop.auth.rest.model.UpdateUserRequest;
import com.monkeyshop.auth.rest.security.CustomPasswordEncoder;
import com.monkeyshop.security.AuthenticationFetcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthDelegateTest {

    @Mock
    private AuthenticationFetcher authenticationFetcher;

    @Mock
    private CustomPasswordEncoder customPasswordEncoder;

    @Mock
    private UserHandler userHandler;

    @InjectMocks
    private AuthDelegate authDelegate;

    @Test
    public void testCreateUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest()
            .username("username")
            .email("email")
            .password("password");

        when(customPasswordEncoder.encode(createUserRequest.getPassword())).thenReturn("encodedPassword");
        when(authenticationFetcher.getLoggedUserID()).thenReturn("author");

        authDelegate.createUser(createUserRequest);

        ArgumentCaptor<UserCreatedEvent> eventCaptor = ArgumentCaptor.forClass(UserCreatedEvent.class);
        verify(userHandler).create(eventCaptor.capture());

        UserCreatedEvent createdEvent = eventCaptor.getValue();
        assertEquals("username", createdEvent.getUsername());
        assertEquals("email", createdEvent.getEmail());
        assertEquals("encodedPassword", createdEvent.getPasswordHash());
        assertEquals("author", createdEvent.getAuthor());
    }

    @Test
    public void testUpdateUser() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest()
            .username("username")
            .email("email")
            .password("password");

        when(customPasswordEncoder.encode(updateUserRequest.getPassword())).thenReturn("encodedPassword");
        when(authenticationFetcher.getLoggedUserID()).thenReturn("author");

        authDelegate.updateUser("userid", updateUserRequest);

        ArgumentCaptor<UserUpdatedEvent> eventCaptor = ArgumentCaptor.forClass(UserUpdatedEvent.class);
        verify(userHandler).update(eventCaptor.capture());

        UserUpdatedEvent updatedEvent = eventCaptor.getValue();
        assertEquals("username", updatedEvent.getUsername());
        assertEquals("email", updatedEvent.getEmail());
        assertEquals("encodedPassword", updatedEvent.getPasswordHash());
        assertEquals("author", updatedEvent.getAuthor());
    }


    @Test
    public void testDeleteUser() {
        when(authenticationFetcher.getLoggedUserID()).thenReturn("author");

        authDelegate.deleteUser("userid");

        ArgumentCaptor<UserDeletedEvent> eventCaptor = ArgumentCaptor.forClass(UserDeletedEvent.class);
        verify(userHandler).delete(eventCaptor.capture());

        UserDeletedEvent deletedEvent = eventCaptor.getValue();
        assertEquals("userid", deletedEvent.getUserId());
        assertEquals("author", deletedEvent.getAuthor());
    }

}