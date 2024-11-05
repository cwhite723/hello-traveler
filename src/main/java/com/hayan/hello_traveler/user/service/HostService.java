package com.hayan.hello_traveler.user.service;

import com.hayan.hello_traveler.common.exception.CustomException;
import com.hayan.hello_traveler.common.response.ErrorCode;
import com.hayan.hello_traveler.user.domain.Host;
import com.hayan.hello_traveler.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HostService {

  private final UserRepository userRepository;


}
