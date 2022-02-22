package com.pkglobal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.google.gson.Gson;
import com.pkglobal.controllers.CustomerPublisherController;
import com.pkglobal.models.Address;
import com.pkglobal.models.Customer;
import com.pkglobal.models.Customer.CustomerStatusEnum;
import com.pkglobal.services.KafkaProducer;

@ExtendWith(MockitoExtension.class)
class CustomerPublisherControllerTest {
  private static final String RESPONSE_STRING =
      "{\"status\":\"Ok\",\"data\":\"Successfully Published the customer\"}";

  @InjectMocks
  private CustomerPublisherController customerPublisherController;

  @Mock
  private KafkaProducer kafkaProducer;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(customerPublisherController).build();
  }

  @Test
  void testPublishServiceWithSuccess() throws Exception {
    Customer customer = builCustomerObject();


    MvcResult result = mockMvc
        .perform(post("/customer/publish").contentType(MediaType.APPLICATION_JSON)
            .headers(buildHttpHeaders()).content(new Gson().toJson(customer)))
        .andExpect(status().is(200)).andReturn();
    String responseString = result.getResponse().getContentAsString();
    assertNotNull(responseString);
    assertEquals(RESPONSE_STRING, responseString);
  }



  private HttpHeaders buildHttpHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");
    headers.set("Authorization", "Bearer da87cff0-eb4d-4ff8-84e4-362f44c64ca4");
    headers.set("Activity-Id", "Activity-Id");
    headers.set("Transaction-Id", "Transaction-Id");
    return headers;
  }

  private Customer builCustomerObject() {
    Customer messageRequest = new Customer();
    Address address = buildAddressObject();
    messageRequest.setAddress(address);
    messageRequest.setCountry("India");
    messageRequest.setCountryCode("IN");
    messageRequest.setBirthDate("27-12-1994");
    messageRequest.setCustomerNumber("C234566667");
    messageRequest.setCustomerStatus(CustomerStatusEnum.OPEN);
    messageRequest.setEmail("samanth@gmail.com");
    messageRequest.setFirstName("Samanth");
    messageRequest.setLastName("M");
    messageRequest.setMobileNumber("9014392013");
    return messageRequest;
  }

  private Address buildAddressObject() {
    Address address = new Address();
    address.setAddressLine1("dsafdsafdsfdsfdafads");
    address.setAddressLine2("fdasfdsafadfdsa");
    address.setStreet("fdsafdsa");
    return address;
  }

}
