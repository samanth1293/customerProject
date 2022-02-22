package com.pkglobal.services;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.pkglobal.models.Customer;

@Component
public class KafkaProducer {

  Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

  @Autowired
  KafkaTemplate<String, Customer> kafkaTemplate;


  public void kafkaProducer(String topic, String key, Customer value) {
    logger.info("Publishing customer into kafka,Topic {} Key {} Value {}", topic, key, value);
    kafkaTemplate.send(topic, key, value);
    kafkaTemplate.flush();
    kafkaTemplate.setCloseTimeout(Duration.ofMillis(100));
    logger.info("Published customer into kafka,Topic {} Key {} Value {}", topic, key, value);
    kafkaTemplate.send(topic, key, value);
  }

}
