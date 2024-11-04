package com.hayan.hello_traveler.redis;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String, String> redisTemplate;

  public void saveRefreshToken(Long userId, String refreshToken, long expirationTime) {
    redisTemplate.opsForValue().set("refreshToken:" + userId, refreshToken, expirationTime, TimeUnit.MILLISECONDS);
  }

  public String getRefreshToken(Long userId) {
    return redisTemplate.opsForValue().get("refreshToken:" + userId);
  }

  public void deleteRefreshToken(Long userId) {
    redisTemplate.delete("refreshToken:" + userId);
  }
}