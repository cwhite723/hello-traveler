package com.hayan.hello_traveler.auth;

import com.hayan.hello_traveler.common.exception.CustomException;
import com.hayan.hello_traveler.common.response.ErrorCode;
import com.hayan.hello_traveler.user.domain.User;
import com.hayan.hello_traveler.user.repository.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = (String) authentication.getCredentials();

    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
    }

    return new UsernamePasswordAuthenticationToken(user, null,
        Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}