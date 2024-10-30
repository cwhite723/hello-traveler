package com.hayan.hello_traveler.user.domain;

import com.hayan.hello_traveler.user.domain.constant.Gender;
import com.hayan.hello_traveler.user.domain.constant.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("host")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Host extends User {

  @Builder
  public Host(String name, String password, String contact, String username,
      Gender gender, LocalDate birthday, Role role) {
    super(name, password, contact, username, gender, birthday, role);
  }
}
