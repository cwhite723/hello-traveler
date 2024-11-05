package com.hayan.hello_traveler.auth;

import com.hayan.hello_traveler.jwt.JwtProperties;
import com.hayan.hello_traveler.jwt.JwtTokenProvider;
import com.hayan.hello_traveler.user.domain.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtTokenProvider jwtTokenProvider;
  private final JwtProperties jwtProperties;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getUsername(), user.getRole());
    String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

    response.setHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);

    Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setMaxAge(
        (int) jwtProperties.getRefreshTokenExpirationTime() / 1000);

    response.addCookie(refreshTokenCookie);
  }
}