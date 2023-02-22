package com.monkeyshop.customer.handler;

import com.monkeyshop.customer.domain.customer.events.CustomerCreatedEvent;
import com.monkeyshop.customer.domain.customer.events.CustomerEvent;
import com.monkeyshop.customer.domain.customer.events.CustomerDeletedEvent;
import com.monkeyshop.customer.domain.customer.events.CustomerUpdatedEvent;
import com.monkeyshop.customer.domain.customer.CustomerAggregate;
import com.monkeyshop.customer.persistence.CustomerCommandRepository;
import com.monkeyshop.customer.persistence.CustomerQueryRepository;
import com.monkeyshop.customer.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerHandler {

    private final CustomerCommandRepository customerCommandRepository;
    private final CustomerQueryRepository customerQueryRepository;
    private final S3Service s3Service;

    public List<CustomerAggregate> findAll() {
        return customerQueryRepository.findAll();
    }

    public Optional<CustomerAggregate> findById(String customerId) {
        return customerQueryRepository.findById(customerId)
            .map(customerAggregate -> {
                if (Objects.nonNull(customerAggregate.getPhotoUrl())) {
                    customerAggregate.setPhotoUrl(s3Service.generatePresignedURL(customerAggregate.getPhotoUrl()));
                }
                return customerAggregate;
            });
    }

    public String create(CustomerCreatedEvent customerCreatedEvent) throws ResponseStatusException {
        return customerCommandRepository.save(customerCreatedEvent).getCustomerId();
    }

    public void update(CustomerUpdatedEvent customerUpdatedEvent) throws ResponseStatusException {
        throwIfEmpty(customerUpdatedEvent.getCustomerId());
        customerCommandRepository.update(customerUpdatedEvent);
    }

    public void delete(CustomerDeletedEvent customerDeletedEvent) throws ResponseStatusException {
        throwIfEmpty(customerDeletedEvent.getCustomerId());
        customerCommandRepository.delete(customerDeletedEvent);
    }

    private CustomerAggregate throwIfEmpty(String customerId) {
        return customerQueryRepository.findById(customerId)
            .orElseThrow(() -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Customer with ID %s does not exists", customerId));
            });
    }

    @Transactional
    public void uploadFile(String customerId, MultipartFile file, String author) throws IOException {
        CustomerAggregate customer = throwIfEmpty(customerId);
        if (Objects.nonNull(customer.getPhotoUrl())) {
            s3Service.deleteFile(customer.getPhotoUrl());
        }

        String photoUrl = s3Service.uploadFile(customerId, file);

        update(new CustomerUpdatedEvent(customerId, author, null, null, photoUrl));
    }
}
