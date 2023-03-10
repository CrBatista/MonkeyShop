package com.monkeyshop.customer.rest.delegate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monkeyshop.customer.domain.customer.AuditAggregate;
import com.monkeyshop.customer.domain.customer.CustomerAggregate;
import com.monkeyshop.customer.domain.customer.events.CustomerCreatedEvent;
import com.monkeyshop.customer.domain.customer.events.CustomerDeletedEvent;
import com.monkeyshop.customer.domain.customer.events.CustomerUpdatedEvent;
import com.monkeyshop.customer.handler.CustomerHandler;
import com.monkeyshop.customer.rest.api.CustomerApiDelegate;
import com.monkeyshop.customer.rest.model.*;
import com.monkeyshop.security.AuthenticationFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public ResponseEntity<Void> deleteCustomer(String customerId) {
        CustomerDeletedEvent customerDeletedEvent = new CustomerDeletedEvent(
            customerId,
            authenticationFetcher.getLoggedUserID());

        customerHandler.delete(customerDeletedEvent);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CustomerDetails> findCustomer(String customerId) {
        return customerHandler.findById(customerId)
            .map(customerAggregate -> new CustomerDetails()
                .id(customerAggregate.getId())
                .name(customerAggregate.getName())
                .surname(customerAggregate.getSurname())
                .photoUrl(customerAggregate.getPhotoUrl())
                .createdAt(customerAggregate.getCreatedAt().atOffset(ZoneOffset.ofHours(-1)))
                .createdBy(new AuditDetails()
                    .id(customerAggregate.getCreatedBy().get_id())
                    .username(customerAggregate.getCreatedBy().getUsername())
                    .email(customerAggregate.getCreatedBy().getEmail()))
                .updatedAt(customerAggregate.getUpdatedAt().atOffset(ZoneOffset.ofHours(-1)))
                .updatedBy(new AuditDetails()
                    .id(customerAggregate.getUpdatedBy().get_id())
                    .username(customerAggregate.getUpdatedBy().getUsername())
                    .email(customerAggregate.getUpdatedBy().getEmail()))
                .lastEvent(customerAggregate.getLastEvent())
                .history(customerAggregate.getHistory().stream().map(customerHistoryAggregate ->
                    new CustomerHistoryDetails()
                        .name(customerHistoryAggregate.getName())
                        .surname(customerHistoryAggregate.getSurname())
                        .photoUrl(customerHistoryAggregate.getPhotoUrl())
                        .timestamp(customerHistoryAggregate.getTimestamp().atOffset(ZoneOffset.ofHours(-1)))
                        .type(customerHistoryAggregate.getType())
                        .author(customerHistoryAggregate.getAuthor())
                ).collect(Collectors.toList())))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Customer>> findCustomers() {
        List<Customer> customers = customerHandler.findAll().stream()
            .map(customerAggregate ->
                new Customer()
                    .id(customerAggregate.getId())
                    .name(customerAggregate.getName())
                    .surname(customerAggregate.getSurname())
                    .photoUrl(customerAggregate.getPhotoUrl()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(customers);
    }

    @Override
    public ResponseEntity<Void> updateCustomer(String customerId, UpdateCustomerRequest updateCustomerRequest) {
        CustomerUpdatedEvent customerUpdatedEvent = new CustomerUpdatedEvent(
            customerId,
            authenticationFetcher.getLoggedUserID(),
            updateCustomerRequest.getName(),
            updateCustomerRequest.getSurname(),
            null);

        customerHandler.update(customerUpdatedEvent);

        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> customerCustomerIdPost(String customerId, MultipartFile file) {
        Optional<CustomerAggregate> customer = customerHandler.findById(customerId);
        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            customerHandler.uploadFile(customerId, file, authenticationFetcher.getLoggedUserID());
        } catch (IOException fileException) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.accepted().build();
    }
}
