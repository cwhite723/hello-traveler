package com.hayan.hello_traveler.aop;

import com.hayan.hello_traveler.auth.service.AuthService;
import com.hayan.hello_traveler.common.annotation.LoginGuestId;
import com.hayan.hello_traveler.common.annotation.LoginHostId;
import com.hayan.hello_traveler.common.exception.CustomException;
import com.hayan.hello_traveler.common.response.ErrorCode;
import com.hayan.hello_traveler.user.service.UserService;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticationAspect {

  private final AuthService authService;
  private final UserService userService;

  @Around("@annotation(com.hayan.hello_traveler.common.annotation.LoginCheck)")
  public Object checkLoginAndInject(ProceedingJoinPoint joinPoint) throws Throwable {
    Long loginId = authService.getLoggedInUserId()
        .orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED));

    String type = userService.getUserType(userService.getById(loginId));

    switch (type) {
      case "host" -> injectArgument(joinPoint, LoginHostId.class, loginId);
      case "guest" -> injectArgument(joinPoint, LoginGuestId.class, loginId);
      default -> throw new CustomException(ErrorCode.INVALID_USER_TYPE);
    }

    return joinPoint.proceed(joinPoint.getArgs());
  }

  private void injectArgument(ProceedingJoinPoint joinPoint, Class<?> annotationClass,
      Long loginId) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Object[] args = joinPoint.getArgs();
    Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();

    IntStream.range(0, parameterAnnotations.length)
        .filter(i -> Arrays.stream(parameterAnnotations[i])
            .anyMatch(annotation -> annotation.annotationType().equals(annotationClass)))
        .findFirst()
        .ifPresent(i -> args[i] = loginId);
  }
}