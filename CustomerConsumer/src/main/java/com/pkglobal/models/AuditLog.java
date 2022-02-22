package com.pkglobal.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AUDIT_LOG")
public class AuditLog implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "CUSTOMER_NUMBER")
  private String customerNumber;

  @Column(name = "PAY_LOAD", columnDefinition = "JSON")
  private String payLoad;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCustomerNumber() {
    return customerNumber;
  }

  public void setCustomerNumber(String customerNumber) {
    this.customerNumber = customerNumber;
  }

  public String getPayLoad() {
    return payLoad;
  }

  public void setPayLoad(String payLoad) {
    this.payLoad = payLoad;
  }



}
