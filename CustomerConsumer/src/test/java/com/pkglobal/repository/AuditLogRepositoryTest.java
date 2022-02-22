package com.pkglobal.repository;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.pkglobal.models.AuditLog;


@SpringBootTest
class AuditLogRepositoryTest {

  @Autowired
  AuditLogRepository auditLogRepository;



  @Test
  void save_thenReturnSuccess() {
    AuditLog auditLog = new AuditLog();
    auditLog.setCustomerNumber("****000001");
    auditLog.setPayLoad(
        "{\"customerNumber\":\"C0000000001\",\"firstName\":\"SamanthMahankali\",\"lastName\":\"SamanthMahankali\",\"birthDate\":\"2022-02-15\",\"country\":\"India\",\"countryCode\":\"IN\",\"mobileNumber\":9014392103,\"email\":\"user@example.com\",\"customerStatus\":\"Open\",\"address\":{\"addressLine1\":\"string\",\"addressLine2\":\"string\",\"street\":\"string\"}}");
    AuditLog auditLog1 = auditLogRepository.save(auditLog);
    Assertions.assertTrue(auditLog1.getId() >= 1);
  }
}
