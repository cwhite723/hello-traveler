package com.hayan.hello_traveler.jwt;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtProperties {

  private static final Dotenv dotenv = Dotenv.load();

  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public final Key secretKey = Keys.hmacShaKeyFor(
      Decoders.BASE64.decode(dotenv.get("JWT_SECRET_KEY")));
  private final long accessTokenExpirationTime = Long.parseLong(
      dotenv.get("ACCESS_TOKEN_EXPIRATION_TIME"));
  private final long refreshTokenExpirationTime = Long.parseLong(
      dotenv.get("REFRESH_TOKEN_EXPIRATION_TIME"));
}