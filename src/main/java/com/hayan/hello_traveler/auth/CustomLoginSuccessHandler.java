package com.hayan.hello_traveler.auth;

import com.hayan.hello_traveler.jwt.JwtProperties;
import com.hayan.hello_traveler.jwt.JwtTokenProvider;
import com.hayan.hello_traveler.user.domain.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getUsername(),
        Collections.singleton(user.getRole().name()));
    String refreshToken = jwtTokenProvider.generateRefreshToken();

    response.setHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);

    Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setMaxAge(
        (int) jwtTokenProvider.getREFRESH_TOKEN_EXPIRATION_TIME() / 1000);

    response.addCookie(refreshTokenCookie);
  }
}