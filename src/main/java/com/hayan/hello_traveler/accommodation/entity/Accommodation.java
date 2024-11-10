package com.hayan.hello_traveler.accommodation.entity;

import com.hayan.hello_traveler.accommodation.entity.constant.Type;
import com.hayan.hello_traveler.accommodation.entity.dto.AccommodationRequest;
import com.hayan.hello_traveler.common.entity.BaseEntity;
import com.hayan.hello_traveler.reservation.entity.Review;
import com.hayan.hello_traveler.user.domain.Host;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@Table(name = "accommodations",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "address"})
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at IS NULL")
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

  @Enumerated(EnumType.STRING)
  private Type type;

  private String description;

  @Column(name = "checkin_time")
  private LocalTime checkinTime;

  @Column(name = "checkout_time")
  private LocalTime checkoutTime;

  @Column(name = "day_off")
  @ElementCollection(targetClass = DayOfWeek.class)
  @CollectionTable(name = "accommodation_day_off", joinColumns = @JoinColumn(name = "accommodation_id"))
  @Enumerated(EnumType.STRING)
  private List<DayOfWeek> dayOff = new ArrayList<>();

  @Column(name = "total_rating_score")
  private int totalRatingScore;

  @Column(name = "review_count")
  private int reviewCount;

  @Column(name = "bookmark_count")
  private int bookmarkCount;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "host_id", nullable = false)
  private Host host;

  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
  private final List<Room> rooms = new ArrayList<>();

  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
  private final List<AccommodationAmenity> accommodationAmenities = new ArrayList<>();

  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
  private final List<Review> reviews = new ArrayList<>();

  @Builder
  public Accommodation(String name, String contact, String address, String city, String district,
      double latitude, double longitude, Type type, String description,
      LocalTime checkinTime, LocalTime checkoutTime, List<DayOfWeek> dayOff, Host host) {
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
    this.dayOff = dayOff;
    this.host = host;
  }

  public void update(AccommodationRequest request) {
    this.name = request.name();
    this.contact = request.contact();
    this.address = request.address();
    this.city = request.city();
    this.district = request.district();
    this.latitude = request.latitude();
    this.longitude = request.longitude();
    this.type = request.type();
    this.description = request.description();
    this.checkinTime = request.checkinTime();
    this.checkoutTime = request.checkoutTime();
    this.dayOff = request.dayOff();
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

  public double calculateAverageRating() {
    return reviewCount > 0 ? (double) totalRatingScore / reviewCount : 0.0;
  }

  public void addReview(int rating) {
    reviewCount++;
    totalRatingScore += rating;
  }

  public void updateReview(int oldRating, int newRating) {
    totalRatingScore = totalRatingScore - oldRating + newRating;
  }

  public void removeReview(int rating) {
    reviewCount--;
    totalRatingScore -= rating;
  }
}
