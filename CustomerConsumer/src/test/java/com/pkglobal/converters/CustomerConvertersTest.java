package com.pkglobal.converters;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import com.pkglobal.models.Customer;

class CustomerConvertersTest {

  @Test
  void customerMasking_thenReturnSuccessfully() {
    Customer customer = new Customer();
    customer.setEmail("abc@gmail.com");
    customer.setFirstName("abcde");
    customer.setBirthDate("24-12-1990");
    Customer maskingCustomer = new Customer();
    maskingCustomer.setEmail("****gmail.com");
    maskingCustomer.setFirstName("****e");
    maskingCustomer.setBirthDate("****2-1990");
    MockedStatic<CustomerConveters> utilities = Mockito.mockStatic(CustomerConveters.class);
    utilities.when(() -> CustomerConveters.customerMasking(customer)).thenReturn(maskingCustomer);
    Assertions.assertEquals(CustomerConveters.customerMasking(customer), maskingCustomer);
  }

}

