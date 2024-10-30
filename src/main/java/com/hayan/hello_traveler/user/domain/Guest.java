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
@DiscriminatorValue("guest")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guest extends User {

  @Builder
  public Guest(String name, String password, String contact, String username,
      Gender gender, LocalDate birthday, Role role) {
    super(name, password, contact, username, gender, birthday, role);
  }
}
