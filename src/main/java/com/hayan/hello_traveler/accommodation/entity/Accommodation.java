package com.hayan.hello_traveler.accommodation.entity;

import com.hayan.hello_traveler.accommodation.entity.constant.Type;
import com.hayan.hello_traveler.common.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "accommodations",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "address"})
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accommodation extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String contact;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String district;

  @Column(nullable = false)
  private double latitude;

  @Column(nullable = false)
  private double longitude;

  private Type type;

  private String description;

  @Column(name = "checkin_time")
  private LocalTime checkinTime;

  @Column(name = "checkout_time")
  private LocalTime checkoutTime;

  @Column(name = "rating_average")
  private double ratingAverage;

  @Column(name = "review_count")
  private int reviewCount;

  @Column(name = "bookmark_count")
  private int bookmarkCount;

  @Column(name = "deleted_at")
  private boolean deletedAt;

  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
  private final List<AccommodationAmenity> accommodationAmenities = new ArrayList<>();

  @Builder
  public Accommodation(String name, String contact, String address, String city, String district,
      double latitude, double longitude, Type type, String description,
      LocalTime checkinTime, LocalTime checkoutTime) {
    this.name = name;
    this.contact = contact;
    this.address = address;
    this.city = city;
    this.district = district;
    this.latitude = latitude;
    this.longitude = longitude;
    this.type = type;
    this.description = description;
    this.checkinTime = checkinTime;
    this.checkoutTime = checkoutTime;
  }
}
