package com.hayan.hello_traveler.user.service.factory;

import com.hayan.hello_traveler.auth.domain.dto.request.SignUpRequest;
import com.hayan.hello_traveler.user.domain.Guest;
import com.hayan.hello_traveler.user.domain.constant.Role;
import org.springframework.stereotype.Component;

@Component("guest")
public class GuestFactory implements UserFactory {

  private final Role ROLE = Role.USER;

  @Override
  public Guest createUser(SignUpRequest request, String encodedPassword) {

    return Guest.builder()
        .name(request.name())
        .password(encodedPassword)
        .username(request.username())
        .contact(request.contact())
        .birthday(request.birthday())
        .gender(request.gender())
        .role(ROLE)
        .build();
  }
}
