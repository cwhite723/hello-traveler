package com.hayan.hello_traveler.auth.service;

import com.hayan.hello_traveler.auth.domain.dto.request.SignInRequest;
import com.hayan.hello_traveler.auth.domain.dto.response.SignInResponse;
import com.hayan.hello_traveler.auth.domain.dto.response.UserInfo;
import com.hayan.hello_traveler.jwt.JwtTokenProvider;
import com.hayan.hello_traveler.user.domain.User;
import com.hayan.hello_traveler.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;

  public SignInResponse getUserInfo(SignInRequest request) {
    User user = userService.getByUsername(request.username());
    String type = userService.getUserType(user);

    return new SignInResponse(user.getId(), user.getUsername(), type);
  }


  public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = jwtTokenProvider.getTokenFromRequest(request);
    Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
    UserInfo user = userService.getUserInfoById(userId);
    String newAccessToken = jwtTokenProvider.refreshAccessToken(userId, user, refreshToken);

    response.setHeader("Authorization", "Bearer " + newAccessToken);
  }

  public Optional<Long> getLoggedInUserId() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String token = jwtTokenProvider.getTokenFromRequest(request);

    if (token == null || token.isEmpty()) {
      return Optional.empty();
    }

    return Optional.ofNullable(jwtTokenProvider.getUserIdFromToken(token));
  }
}
