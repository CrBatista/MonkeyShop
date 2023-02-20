package com.monkeyshop.customer.domain.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Setter
@Getter
@Builder
@Document(value="customers") // View. Only Read.
public class CustomerAggregate {

    @Id
    @Field("_id")
    private String id;

    @Field("data.name")
    private String name;

    @Field("data.surname")
    private String surname;

    @Field("data.photoUrl")
    private String photoUrl;

    @Field("createdBy")
    private AuditAggregate createdBy;

    @Field("createdAt")
    private Instant createdAt;

    @Field("updatedBy")
    private AuditAggregate updatedBy;

    @Field("updatedAt")
    private Instant updatedAt;

    @Field("lastEvent")
    private String lastEvent;

    @Field("history")
    private List<CustomerHistoryAggregate> history;

}
