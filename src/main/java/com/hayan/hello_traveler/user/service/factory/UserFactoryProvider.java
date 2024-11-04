package com.hayan.hello_traveler.user.service.factory;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactoryProvider {

  private final Map<String, UserFactory> userFactories;

  public UserFactory getFactory(String type) {

    return userFactories.get(type.toLowerCase());
  }
}
