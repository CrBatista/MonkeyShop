package com.monkeyshop.customer.handler;

import com.monkeyshop.customer.domain.customer.events.CustomerCreatedEvent;
import com.monkeyshop.customer.domain.customer.events.CustomerDeletedEvent;
import com.monkeyshop.customer.domain.customer.CustomerAggregate;
import com.monkeyshop.customer.persistence.CustomerCommandRepository;
import com.monkeyshop.customer.persistence.CustomerQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerHandlerTest {

    @Mock
    private CustomerCommandRepository customerCommandRepository;

    @Mock
    private CustomerQueryRepository customerQueryRepository;

    @InjectMocks
    private CustomerHandler customerHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        CustomerAggregate user = mock(CustomerAggregate.class);
        when(customerQueryRepository.findAll()).thenReturn(Collections.singletonList(user));

        assertEquals(Collections.singletonList(user), customerHandler.findAll());
        verify(customerQueryRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        CustomerAggregate user = mock(CustomerAggregate.class);
        when(customerQueryRepository.findById("userId")).thenReturn(Optional.of(user));

        assertEquals(Optional.of(user), customerHandler.findById("userId"));
        verify(customerQueryRepository, times(1)).findById("userId");
    }

    @Test
    public void testCreate() {
        CustomerCreatedEvent customerCreatedEvent = mock(CustomerCreatedEvent.class);
        when(customerCreatedEvent.getCustomerId()).thenReturn("customerId");

        when(customerCommandRepository.save(any(CustomerCreatedEvent.class))).thenReturn(customerCreatedEvent);

        assertEquals("customerId", customerHandler.create(customerCreatedEvent));
        verify(customerCommandRepository, times(1)).save(customerCreatedEvent);
    }

    @Test
    public void delete_withValidUserId_deletesUser() {
        String customerId = "customerId";
        String author = "author";
        CustomerDeletedEvent customerDeletedEvent = new CustomerDeletedEvent(customerId, author);
        when(customerQueryRepository.findById(customerId)).thenReturn(Optional.of(mock(CustomerAggregate.class)));

        customerHandler.delete(customerDeletedEvent);

        verify(customerQueryRepository, times(1)).findById(customerId);
        verify(customerCommandRepository, times(1)).delete(customerDeletedEvent);
    }

    @Test
    public void delete_withInvalidCustomerId_throwsResponseStatusException() {
        String userId = "customerId";
        String author = "author";

        CustomerDeletedEvent customerDeletedEvent = new CustomerDeletedEvent(userId, author);
        when(customerQueryRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            customerHandler.delete(customerDeletedEvent));

        String expectedMessage = String.format("400 BAD_REQUEST \"Customer with ID %s does not exists\"", userId);
        assertEquals(expectedMessage, exception.getMessage());
    }
}