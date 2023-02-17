package com.monkeyshop.auth.domain.user;

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
@Document(value="users") // View. Only Read.
public class UserAggregate implements UserDetails {

    @Id
    @Field("_id")
    private String id;

    @Field("data.username")
    private String username;

    @Field("data.passwordHash")
    private String passwordHash;

    @Field("data.email")
    private String email;

    @Field("data.role")
    private String role;

    @Field("createdBy")
    private String createdBy;

    @Field("createdAt")
    private Instant createdAt;

    @Field("updatedBy")
    private String updatedBy;

    @Field("updatedAt")
    private Instant updatedAt;

    @Field("lastEvent")
    private String lastEvent;

    @Field("history")
    private List<UserHistoryAggregate> history;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE;
    }
}
