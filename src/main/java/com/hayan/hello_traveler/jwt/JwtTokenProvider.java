package com.hayan.hello_traveler.jwt;

import com.hayan.hello_traveler.auth.domain.dto.response.UserInfo;
import com.hayan.hello_traveler.common.exception.CustomException;
import com.hayan.hello_traveler.common.response.ErrorCode;
import com.hayan.hello_traveler.redis.RedisService;
import com.hayan.hello_traveler.user.domain.constant.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final JwtProperties jwtProperties;
  private final RedisService redisService;

  private Claims getClaimsFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(jwtProperties.secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String generateAccessToken(Long userId, String username, Role role) {
    return Jwts.builder()
        .setSubject(username)
        .claim("userId", userId)
        .claim("role", role)
        .setIssuedAt(new Date())
        .setExpiration(
            new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpirationTime()))
        .signWith(jwtProperties.secretKey)
        .compact();
  }

  public String generateRefreshToken(Long userId) {
    String refreshToken = Jwts.builder()
        .setIssuedAt(new Date())
        .setExpiration(
            new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpirationTime()))
        .signWith(jwtProperties.secretKey)
        .compact();

    redisService.saveRefreshToken(userId, refreshToken,
        jwtProperties.getRefreshTokenExpirationTime());
    return refreshToken;
  }

  public boolean validateToken(String token) {
    try {
      getClaimsFromToken(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    return null;
  }

  public Long getUserIdFromToken(String token) {
    Claims claims = getClaimsFromToken(token);
    return claims.get("userId", Long.class);
  }

  public String getUsernameFromToken(String token) {
    return getClaimsFromToken(token).getSubject();
  }

  public Set<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
    Claims claims = getClaimsFromToken(token);
    @SuppressWarnings("unchecked")
    String role = claims.get("role", String.class);
    return Set.of(new SimpleGrantedAuthority(role));
  }

  public String refreshAccessToken(Long userId, UserInfo user, String refreshToken) {
    String storedRefreshToken = redisService.getRefreshToken(userId);
    if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
      throw new CustomException(ErrorCode.INVALID_TOKEN);
    }
    if (!validateToken(refreshToken)) {
      throw new CustomException(ErrorCode.EXPIRED_TOKEN);
    }
    return generateAccessToken(userId, user.username(), user.role());
  }
}