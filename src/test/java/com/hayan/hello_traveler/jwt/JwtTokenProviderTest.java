package com.hayan.hello_traveler.jwt;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hayan.hello_traveler.auth.domain.dto.response.UserInfo;
import com.hayan.hello_traveler.config.TestRedisConfig;
import com.hayan.hello_traveler.user.domain.constant.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestRedisConfig.class)
public class JwtTokenProviderTest {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  private final Long userId = 1L;
  private final String username = "testuser";
  private final Role role = Role.USER;
  private final UserInfo userInfo = new UserInfo(username, role);

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void 유효한_토큰은_검증에_성공한다() {
    String accessToken = jwtTokenProvider.generateAccessToken(userId, username, role);
    assertTrue(jwtTokenProvider.validateToken(accessToken));
  }

  @Test
  public void 유효하지_않은_토큰은_검증에_실패한다() {
    String invalidToken = "invalid.token.value";
    assertFalse(jwtTokenProvider.validateToken(invalidToken));
  }

  @Test
  public void 리프레시_토큰이_유효하면_액세스_토큰을_재발급한다() {
    String refreshToken = jwtTokenProvider.generateRefreshToken(userId);
    String newAccessToken = jwtTokenProvider.refreshAccessToken(userId, userInfo, refreshToken);

    assertNotNull(newAccessToken);
    assertTrue(jwtTokenProvider.validateToken(newAccessToken));
  }
}