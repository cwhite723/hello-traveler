package com.hayan.hello_traveler.common.exception;

import com.hayan.hello_traveler.common.response.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public CustomException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

<<<<<<< HEAD
}
=======
}
>>>>>>> 1cf69b7cf30a06bd0924267b04fb5042846c668d
