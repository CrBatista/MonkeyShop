package com.monkeyshop.auth.kafka.producer;

import com.monkeyshop.auth.domain.events.UserCreatedEvent;
import com.monkeyshop.auth.domain.events.UserDeletedEvent;
import com.monkeyshop.auth.domain.events.UserUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

  @Value("${kafka.topics.user.created.name}")
  private String USER_CREATED_TOPIC_NAME;

  @Value("${kafka.topics.user.updated.name}")
  private String USER_UPDATED_TOPIC_NAME;

  @Value("${kafka.topics.user.deleted.name}")
  private String USER_DELETED_TOPIC_NAME;

  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  public void sendMessage(UserCreatedEvent userCreatedEvent) {
    kafkaTemplate.send(USER_CREATED_TOPIC_NAME, userCreatedEvent);
  }

  public void sendMessage(UserUpdatedEvent userUpdatedEvent) {
    kafkaTemplate.send(USER_UPDATED_TOPIC_NAME, userUpdatedEvent);
  }

  public void sendMessage(UserDeletedEvent userDeletedEvent) {
    kafkaTemplate.send(USER_DELETED_TOPIC_NAME, userDeletedEvent);
  }
}
