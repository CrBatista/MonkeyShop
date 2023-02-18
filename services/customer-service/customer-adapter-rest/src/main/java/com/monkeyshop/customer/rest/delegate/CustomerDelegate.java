package com.monkeyshop.customer.rest.delegate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monkeyshop.customer.domain.events.CustomerCreatedEvent;
import com.monkeyshop.customer.handler.CustomerHandler;
import com.monkeyshop.customer.rest.api.CustomerApiDelegate;
import com.monkeyshop.customer.rest.model.CreateCustomerRequest;
import com.monkeyshop.security.AuthenticationFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@PreAuthorize("hasAnyAuthority ('ADMIN', 'USER')")
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDelegate implements CustomerApiDelegate {

    private final CustomerHandler customerHandler;
    private final AuthenticationFetcher authenticationFetcher;

    @Override
    public ResponseEntity<String> createCustomer(CreateCustomerRequest createCustomerRequest) {
        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent(
            authenticationFetcher.getLoggedUserID(),
            createCustomerRequest.getName(),
            createCustomerRequest.getSurname());

        String userId = customerHandler.create(customerCreatedEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }
}
