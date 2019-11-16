package com.sduwh.sso.controller;

import com.sduwh.sso.constant.ErrorCode;
import com.sduwh.sso.exception.AbstractSsoException;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;

/**
 * MVC异常处理
 *
 * @author wxp
 */
@ControllerAdvice
@RestController
public class ExceptionController {
  @ExceptionHandler(value = {InvalidParameterException.class, InvalidClientException.class})
  public ResponseEntity<ExceptionResponse> handleInvalidParameterException(Exception err) {
    ExceptionResponse response = new ExceptionResponse();
    response.setCode(ErrorCode.INPUT_FIELD_INVALID.getCode());
    response.setMessage(ErrorCode.INPUT_FIELD_INVALID.name());
    return ResponseEntity.status(400).body(response);
  }

  @ExceptionHandler(InvalidGrantException.class)
  public ResponseEntity<ExceptionResponse> handleInvalidGrantException(InvalidGrantException err) {
    ExceptionResponse response = new ExceptionResponse();
    response.setCode(ErrorCode.INVALID_GRANT.getCode());
    response.setMessage(ErrorCode.INVALID_GRANT.name());
    return ResponseEntity.status(400).body(response);
  }

  @ExceptionHandler(AbstractSsoException.class)
  public ResponseEntity<ExceptionResponse> handleSsoException(AbstractSsoException err) {
    ExceptionResponse response = new ExceptionResponse();
    response.setCode(err.getErrorCode().getCode());
    response.setMessage(err.getMessage());
    return ResponseEntity.status(400).body(response);
  }

  @Data
  private static class ExceptionResponse {
    private int code;
    private String message;
  }
}
