package com.pkglobal.controllers;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.pkglobal.converters.CustomerConveters;
import com.pkglobal.models.Customer;
import com.pkglobal.models.SuccessResponse;
import com.pkglobal.services.KafkaProducer;

@RestController
public class CustomerPublisherController {

  Logger logger = LoggerFactory.getLogger(CustomerPublisherController.class);

  @Autowired
  KafkaProducer kafkaProducer;

  @PostMapping(path = "/publish", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> publishCustomer(
      @RequestHeader("Transaction-Id") String transactionId,
      @RequestHeader("Activity-Id") String activityId, @RequestBody @Valid Customer customer) {

    // masking customer data
    Customer maskedCustomerData = CustomerConveters.customerMasking(customer);
    logger.info("CustomerData Received : {}", maskedCustomerData);

    // invoking kafka producer service
    logger.info(
        "Publishing customer data with TransactionId= {} , ActivityId {} and Customer Data {}",
        transactionId, activityId, maskedCustomerData);
    long starttime = System.currentTimeMillis();
    kafkaProducer.kafkaProducer("customer_data", transactionId + "_" + activityId, customer);
    logger.info(
        "Published customer data with TransactionId= {} , ActivityId {} and Customer Data {}",
        transactionId, activityId, maskedCustomerData);
    logger.info("Kafka Producer ResponseTime={}", (System.currentTimeMillis() - starttime));
    SuccessResponse response = buildSuccessResponse();

    return new ResponseEntity<>(response, HttpStatus.OK);

  }


  public SuccessResponse buildSuccessResponse() {
    SuccessResponse response = new SuccessResponse();
    response.setStatus("Ok");
    response.setMessage("Successfully Published the customer");
    return response;
  }

}
