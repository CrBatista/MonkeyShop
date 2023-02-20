package com.monkeyshop.customer.kafka.listener;

import com.monkeyshop.customer.domain.user.UserCreatedEvent;
import com.monkeyshop.customer.domain.user.UserDeletedEvent;
import com.monkeyshop.customer.domain.user.UserUpdatedEvent;
import com.monkeyshop.customer.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserListener {

  private final UserHandler userHandler;

  @KafkaListener(topics = "${kafka.topics.user.created.name}", groupId = "group_id", containerFactory = "kafkaListenerUserCreatedContainerFactory")
  public void listenUserCreated(UserCreatedEvent userCreatedEvent) {
    userHandler.create(userCreatedEvent);
  }

  @KafkaListener(topics = "${kafka.topics.user.updated.name}", groupId = "group_id", containerFactory = "kafkaListenerUserUpdatedContainerFactory")
  public void listenUserUpdated(UserUpdatedEvent userUpdatedEvent) {
    userHandler.update(userUpdatedEvent);
  }

  @KafkaListener(topics = "${kafka.topics.user.deleted.name}", groupId = "group_id", containerFactory = "kafkaListenerUserUpdatedContainerFactory")
  public void listenUserDeleted(UserDeletedEvent userDeletedEvent) {
    userHandler.delete(userDeletedEvent);
  }
}
