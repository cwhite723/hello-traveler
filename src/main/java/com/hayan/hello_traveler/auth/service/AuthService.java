package com.hayan.hello_traveler.auth.service;

import com.hayan.hello_traveler.auth.domain.dto.request.SignInRequest;
import com.hayan.hello_traveler.auth.domain.dto.response.SignInResponse;
import com.hayan.hello_traveler.common.exception.CustomException;
import com.hayan.hello_traveler.common.response.ErrorCode;
import com.hayan.hello_traveler.jwt.JwtTokenProvider;
import com.hayan.hello_traveler.user.domain.Guest;
import com.hayan.hello_traveler.user.domain.Host;
import com.hayan.hello_traveler.user.domain.User;
import com.hayan.hello_traveler.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;

  public SignInResponse getUserInfo(SignInRequest request) {
    User user = userService.getByUsername(request.username());
    String type = getUserType(user);

    return new SignInResponse(user.getId(), user.getUsername(), type);
  }

  private String getUserType(User user) {
    if (user instanceof Host) {
      return "host";
    } else if (user instanceof Guest) {
      return "guest";
    } else {
      throw new CustomException(ErrorCode.INVALID_USER_TYPE);
    }
  }

  public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = jwtTokenProvider.getTokenFromRequest(request);
    String newAccessToken = jwtTokenProvider.refreshAccessToken(refreshToken);
    response.setHeader("Authorization", "Bearer " + newAccessToken);
  }
}
