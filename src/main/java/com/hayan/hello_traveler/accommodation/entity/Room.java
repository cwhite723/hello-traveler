package com.hayan.hello_traveler.accommodation.entity;

import com.hayan.hello_traveler.accommodation.entity.dto.RoomRequest;
import com.hayan.hello_traveler.common.entity.BaseIdEntity;
import com.hayan.hello_traveler.user.domain.constant.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accommodation_id", nullable = false)
  private Accommodation accommodation;

  @OneToMany(mappedBy = "room", cascade = CascadeType.PERSIST)
  private final List<DailyRoomStock> dailyRoomStocks = new ArrayList<>();

  public void update(RoomRequest request) {
    this.name = request.name();
    this.gender = request.gender();
    this.capacity = request.capacity();
    this.description = request.description();
  }
}
