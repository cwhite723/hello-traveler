package com.hayan.hello_traveler.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayan.hello_traveler.auth.CustomAuthenticationFilter;
import com.hayan.hello_traveler.auth.CustomLoginSuccessHandler;
import com.hayan.hello_traveler.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomLoginSuccessHandler customLoginSuccessHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final ObjectMapper objectMapper;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
        authenticationManager(authenticationConfiguration), objectMapper
    );
    customAuthenticationFilter.setFilterProcessesUrl("/api/auth/sign-in");
    customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler);

    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated()
        )
        .headers(headers -> headers
            .frameOptions(FrameOptionsConfig::sameOrigin)
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilter(customAuthenticationFilter)
        .addFilterBefore(jwtAuthenticationFilter, CustomAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}