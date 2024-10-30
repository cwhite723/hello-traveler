package com.hayan.hello_traveler.user.service;

import com.hayan.hello_traveler.auth.domain.dto.request.SignUpRequest;
import com.hayan.hello_traveler.user.service.factory.UserFactory;
import com.hayan.hello_traveler.user.service.factory.UserFactoryProvider;
import com.hayan.hello_traveler.common.exception.CustomException;
import com.hayan.hello_traveler.common.response.ErrorCode;
import com.hayan.hello_traveler.user.domain.User;
import com.hayan.hello_traveler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserFactoryProvider userFactoryProvider;
  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  @Transactional
  public void save(String type, SignUpRequest request) {
    UserFactory userFactory = userFactoryProvider.getFactory(type);
    if (userFactory == null) {
      throw new CustomException(ErrorCode.REQUEST_VALIDATION_FAIL, "올바르지 않은 회원 유형입니다.");
    }

    User user = userFactory.createUser(request, passwordEncoder.encode(request.password()));
    userRepository.save(user);
  }

  public User getByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, username));
  }
}
