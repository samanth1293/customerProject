package com.pkglobal.exceptions;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.pkglobal.models.ErrorResponse;

@ControllerAdvice
public class CustomerPublisherControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> inValidParamtersExceptionHandler(
      MethodArgumentNotValidException exception) {
    List<String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
        .map(FieldError::getDefaultMessage).distinct().collect(Collectors.toList());
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(HttpStatus.BAD_REQUEST.name());
    errorResponse.setMessage(errorMessages.toString());
    errorResponse.setErrorType(MethodArgumentNotValidException.class.getName());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({InvalidTokenException.class, AuthenticationException.class})
  public ResponseEntity<ErrorResponse> inValidParamterExceptionHandler(Exception exception) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setStatus(HttpStatus.UNAUTHORIZED.name());
    errorResponse.setMessage(exception.getMessage());
    errorResponse.setErrorType(Exception.class.getName());
    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

}
