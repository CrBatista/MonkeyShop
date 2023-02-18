package com.monkeyshop.customer.handler;

import com.monkeyshop.customer.domain.events.CustomerCreatedEvent;
import com.monkeyshop.customer.domain.events.Event;
import com.monkeyshop.customer.domain.events.CustomerDeletedEvent;
import com.monkeyshop.customer.domain.events.CustomerUpdatedEvent;
import com.monkeyshop.customer.domain.customer.CustomerAggregate;
import com.monkeyshop.customer.persistence.CustomerCommandRepository;
import com.monkeyshop.customer.persistence.CustomerQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerHandler {

    private final CustomerCommandRepository customerCommandRepository;
    private final CustomerQueryRepository customerQueryRepository;

    public List<CustomerAggregate> findAll() {
        return customerQueryRepository.findAll();
    }

    public Optional<CustomerAggregate> findById(String customerId) {
        return customerQueryRepository.findById(customerId);
    }

    public String create(CustomerCreatedEvent customerCreatedEvent) throws ResponseStatusException {
        return customerCommandRepository.save(customerCreatedEvent).getCustomerId();
    }

    public void update(CustomerUpdatedEvent customerUpdatedEvent) throws ResponseStatusException {
        throwIfEmpty(customerUpdatedEvent);
        customerCommandRepository.update(customerUpdatedEvent);
    }

    public void delete(CustomerDeletedEvent customerDeletedEvent) throws ResponseStatusException {
        throwIfEmpty(customerDeletedEvent);
        customerCommandRepository.delete(customerDeletedEvent);
    }

    private void throwIfEmpty(Event customerEvent) {
        customerQueryRepository.findById(customerEvent.getCustomerId())
            .orElseThrow(() -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Customer with ID %s does not exists", customerEvent.getCustomerId()));
            });
    }
}
