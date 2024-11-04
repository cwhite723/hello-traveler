package com.hayan.hello_traveler.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayan.hello_traveler.auth.domain.dto.request.SignInRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final ObjectMapper objectMapper;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      SignInRequest signInRequest = objectMapper.readValue(request.getInputStream(),
          SignInRequest.class);
      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(signInRequest.username(),
              signInRequest.password());

      return authenticationManager.authenticate(authToken);
    } catch (IOException e) {
      throw new RuntimeException("로그인 요청 처리 중 오류 발생", e);
    }
  }
}