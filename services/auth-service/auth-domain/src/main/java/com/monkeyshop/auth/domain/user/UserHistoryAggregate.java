package com.monkeyshop.auth.domain.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Setter
@Getter
public class UserHistoryAggregate {

  @Field("type")
  private String type;

  @Field("timestamp")
  private Instant timestamp;

  @Field("data.username")
  private String username;

  @Field("data.passwordHash")
  private String passwordHash;

  @Field("data.email")
  private String email;

  @Field("data.role")
  private String role;

}
