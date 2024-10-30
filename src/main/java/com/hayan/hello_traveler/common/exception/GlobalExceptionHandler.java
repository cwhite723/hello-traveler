package com.hayan.hello_traveler.common.exception;

import com.hayan.hello_traveler.common.response.ApplicationResponse;
import com.hayan.hello_traveler.common.response.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ApplicationResponse<Void>> handleCustomException(CustomException e) {
    return buildErrorResponse(e.getErrorCode(), e.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApplicationResponse<Void>> handleUnexpectedException(RuntimeException e) {
    log.error("Unexpected Exception: {}", e.getMessage(), e);
    return buildErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApplicationResponse<Void>> handleMethodArgumentNotValidException(
      HttpServletRequest request, MethodArgumentNotValidException e) {
    String errorMessage = e.getBindingResult().getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", "));
    log.error("Validation Error: {}, Request URI: {}", errorMessage, request.getRequestURI());
    return buildErrorResponse(ErrorCode.REQUEST_VALIDATION_FAIL, errorMessage);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApplicationResponse<Void>> handleConstraintViolationException(
      HttpServletRequest request, ConstraintViolationException e) {
    log.error("ConstraintViolationException: {}, Request URI: {}", e.getMessage(),
        request.getRequestURI());
    return buildErrorResponse(ErrorCode.REQUEST_VALIDATION_FAIL, e.getMessage());
  }

  private ResponseEntity<ApplicationResponse<Void>> buildErrorResponse(ErrorCode errorCode,
      String message) {
    ApplicationResponse<Void> response = ApplicationResponse.error(errorCode.getCode(), message);
    return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
  }
}
