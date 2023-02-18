package com.monkeyshop.customer.domain.customer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Setter
@Getter
public class CustomerHistoryAggregate {

  @Field("type")
  private String type;

  @Field("timestamp")
  private Instant timestamp;

  @Field("data.name")
  private String name;

  @Field("data.surname")
  private String surname;

  @Field("data.photoUrl")
  private String photoUrl;

}
