package com.hayan.hello_traveler.common.response;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  // 400 Bad Request
  REQUEST_VALIDATION_FAIL(BAD_REQUEST, "400", "잘못된 요청 값입니다."),

  // 401 Unauthorized
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "인증에 실패했습니다."),

  // 403 Forbidden

  // 404 Not Found

  // 409 Conflict

  // 500
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 내부 오류입니다.");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;
}
