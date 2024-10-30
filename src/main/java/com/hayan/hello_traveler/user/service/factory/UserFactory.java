package com.hayan.hello_traveler.user.service.factory;

import com.hayan.hello_traveler.auth.domain.dto.request.SignUpRequest;
import com.hayan.hello_traveler.user.domain.User;

public interface UserFactory {

  User createUser(SignUpRequest signUpRequest, String encodedPassword);
}
