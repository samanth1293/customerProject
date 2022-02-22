package com.pkglobal.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pkglobal.models.AuditLog;
import com.pkglobal.models.Customer;

public class CustomerConveters {

  private CustomerConveters() {

  }

  public static Customer customerMasking(Customer customer) {
    customer.setCustomerNumber(maskString(customer.getCustomerNumber(), 0, 4, '*'));
    customer.setEmail(maskString(customer.getEmail(), 0, 4, '*'));
    customer.setBirthDate(maskString(customer.getBirthDate(), 0, 4, '*'));
    return customer;
  }

  private static String maskString(String strText, int start, int end, char maskChar) {
    int maskLength = end - start;

    if (maskLength == 0)
      return strText;

    StringBuilder sbMaskString = new StringBuilder(maskLength);

    for (int i = 0; i < maskLength; i++) {
      sbMaskString.append(maskChar);
    }

    return strText.substring(0, start) + sbMaskString.toString()
        + strText.substring(start + maskLength);
  }

  public static AuditLog customerDataIntoAuditLogEntity(Customer customer)
      throws JsonProcessingException {
    AuditLog auditLog = new AuditLog();
    auditLog.setCustomerNumber(customer.getCustomerNumber());
    auditLog.setPayLoad(
        new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(customer));
    return auditLog;
  }

}
