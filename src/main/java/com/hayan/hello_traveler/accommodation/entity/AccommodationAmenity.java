package com.hayan.hello_traveler.accommodation.entity;

import com.hayan.hello_traveler.common.entity.BaseIdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "accommodation_amenities")
public class AccommodationAmenity extends BaseIdEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accommodation_id", nullable = false)
  private Accommodation accommodation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "amenity_id", nullable = false)
  private Amenity amenity;
}
