package com.monkeyshop.customer.kafka.config;

import com.monkeyshop.customer.domain.user.UserCreatedEvent;
import com.monkeyshop.customer.domain.user.UserDeletedEvent;
import com.monkeyshop.customer.domain.user.UserUpdatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Value("${kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomDeserializer.class);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

    return props;
  }

  @Bean
  public ConsumerFactory<String, UserCreatedEvent> consumerUserCreatedEventFactory() {
    return new DefaultKafkaConsumerFactory<>(
        consumerConfigs(),
        new StringDeserializer(),
        new JsonDeserializer<>(UserCreatedEvent.class));
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UserCreatedEvent>> kafkaListenerUserCreatedContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerUserCreatedEventFactory());
    return factory;
  }

  @Bean
  public ConsumerFactory<String, UserUpdatedEvent> consumerUserUpdatedEventFactory() {
    return new DefaultKafkaConsumerFactory<>(
        consumerConfigs(),
        new StringDeserializer(),
        new JsonDeserializer<>(UserUpdatedEvent.class));
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UserUpdatedEvent>> kafkaListenerUserUpdatedContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, UserUpdatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerUserUpdatedEventFactory());
    return factory;
  }

  @Bean
  public ConsumerFactory<String, UserDeletedEvent> consumerUserDeletedEventFactory() {
    return new DefaultKafkaConsumerFactory<>(
        consumerConfigs(),
        new StringDeserializer(),
        new JsonDeserializer<>(UserDeletedEvent.class));
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UserDeletedEvent>> kafkaListenerUserDeletedContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, UserDeletedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerUserDeletedEventFactory());
    return factory;
  }

  @Bean
  public KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry() {
    return new KafkaListenerEndpointRegistry();
  }
}
