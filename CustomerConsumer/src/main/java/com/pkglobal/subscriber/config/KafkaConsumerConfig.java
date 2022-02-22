package com.pkglobal.subscriber.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.pkglobal.models.Customer;
import com.pkglobal.models.ErrorLog;
import com.pkglobal.repository.ErrorLogRepository;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {


  public ErrorLogRepository errorLogRepository;

  Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

  @Bean
  public ConsumerFactory<String, Customer> consumerFactory() {
    Map<String, Object> consumerConfig = new HashMap<>();
    consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
    consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "customer_group");
    consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class.getName());
    consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        JsonDeserializer.class.getName());
    return new DefaultKafkaConsumerFactory<>(consumerConfig, new StringDeserializer(),
        new JsonDeserializer<>(Customer.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Customer> kafkaListnerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Customer> kafkaListenerContainerFactory =
        new ConcurrentKafkaListenerContainerFactory<>();
    kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
    kafkaListenerContainerFactory.setErrorHandler((ex, data) -> {
      logger.error(String.format("Error in consuming the data %s", ex.getCause()));
      ErrorLog errorLog = new ErrorLog();
      errorLog.setErrorMessage(ex.getMessage());
      errorLog.setErrorType("KAFKA_ERROR");
      errorLogRepository.save(errorLog);
    });
    return kafkaListenerContainerFactory;
  }


}
