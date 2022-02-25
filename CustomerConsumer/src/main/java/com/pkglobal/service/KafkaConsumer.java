package com.pkglobal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pkglobal.converters.CustomerConveters;
import com.pkglobal.models.AuditLog;
import com.pkglobal.models.Customer;
import com.pkglobal.repository.AuditLogRepository;

@Service
public class KafkaConsumer {

  @Autowired
  AuditLogRepository auditLogRepository;

  Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

  @KafkaListener(topics = "customer_data", groupId = "customer_group",
      containerFactory = "kafkaListnerContainerFactory")
  public void consumer(Customer customer, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key)
      throws JsonProcessingException {

    // Customer masking
    Customer maskingCustomerData = CustomerConveters.customerMasking(customer);
    logger.info("Recevied customer data : {}", maskingCustomerData);

    // Converting pojo to entity
    AuditLog auditLog = CustomerConveters.customerDataIntoAuditLogEntity(maskingCustomerData);

    // Inserting customer data into database
    logger.info("Inserting customer data into database");
    long startTime = System.currentTimeMillis();
    auditLogRepository.save(auditLog);
    logger.info("DataBase Service Reponse Time for Key {} : {}", key,
        (System.currentTimeMillis() - startTime));
    logger.info("Inserted customer data into database");

  }

}
