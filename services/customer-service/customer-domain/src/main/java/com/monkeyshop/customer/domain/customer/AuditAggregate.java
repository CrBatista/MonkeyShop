package com.monkeyshop.customer.domain.customer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Setter
@Getter
public class AuditAggregate {

  @Field("_id")
  private String _id;

  @Field("username")
  private String username;

  @Field("email")
  private String email;

}
