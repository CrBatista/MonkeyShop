package com.monkeyshop.customer.kafka.config;

import com.monkeyshop.customer.domain.user.UserCreatedEvent;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.support.serializer.JsonDeserializer;


public class CustomDeserializer<T> extends JsonDeserializer<T> {

  public CustomDeserializer() {
  }

  @Override
  public T deserialize(String topic, Headers headers, byte[] data) {
    JsonDeserializer<T> userCreatedEventJsonDeserializer = new JsonDeserializer<>();
    userCreatedEventJsonDeserializer.addTrustedPackages("*");
//    userCreatedEventJsonDeserializer.setUseTypeHeaders(false);
    userCreatedEventJsonDeserializer.ignoreTypeHeaders();

    return userCreatedEventJsonDeserializer.deserialize(topic, data);
  }
}
