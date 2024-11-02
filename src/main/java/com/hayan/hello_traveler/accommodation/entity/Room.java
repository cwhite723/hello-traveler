package com.hayan.hello_traveler.accommodation.entity;

import com.hayan.hello_traveler.common.entity.BaseIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "rooms")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseIdEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Gender gender;

  @Column(nullable = false)
  private int capacity;

  private String description;
}
