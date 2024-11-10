package com.hayan.hello_traveler.user.domain;

import com.hayan.hello_traveler.common.entity.BaseEntity;
import com.hayan.hello_traveler.user.domain.constant.Gender;
import com.hayan.hello_traveler.user.domain.constant.Role;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@DiscriminatorColumn(name = "user_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String contact;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(nullable = false)
  private LocalDate birthday;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  public User(String name, String password, String contact, String username, Gender gender,
      LocalDate birthday, Role role) {
    this.name = name;
    this.password = password;
    this.contact = contact;
    this.username = username;
    this.gender = gender;
    this.birthday = birthday;
    this.role = role;
  }
}