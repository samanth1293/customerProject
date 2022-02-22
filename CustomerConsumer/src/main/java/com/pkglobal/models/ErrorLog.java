package com.pkglobal.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ERROR_LOG")
public class ErrorLog implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "ERROR_TYPE")
  private String errorType;

  @Column(name = "ERROR_MESSAGE")
  private String errorMessage;

  @Column(name = "PAY_LOAD", columnDefinition = "JSON")
  private String payLoad;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getErrorType() {
    return errorType;
  }

  public void setErrorType(String errorType) {
    this.errorType = errorType;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getPayLoad() {
    return payLoad;
  }

  public void setPayLoad(String payLoad) {
    this.payLoad = payLoad;
  }



}
