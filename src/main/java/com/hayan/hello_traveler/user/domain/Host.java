package com.hayan.hello_traveler.user.domain;

import com.hayan.hello_traveler.accommodation.entity.Accommodation;
import com.hayan.hello_traveler.reservation.entity.Reservation;
import com.hayan.hello_traveler.user.domain.constant.Gender;
import com.hayan.hello_traveler.user.domain.constant.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("host")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Host extends User {

  @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST)
  private final List<Accommodation> accommodations = new ArrayList<>();

  @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST)
  private final List<Reservation> reservations = new ArrayList<>();

  @Builder
  public Host(String name, String password, String contact, String username,
      Gender gender, LocalDate birthday, Role role) {
    super(name, password, contact, username, gender, birthday, role);
  }
}
