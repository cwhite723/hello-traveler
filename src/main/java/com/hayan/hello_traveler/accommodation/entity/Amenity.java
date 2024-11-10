package com.hayan.hello_traveler.accommodation.entity;

import com.hayan.hello_traveler.common.entity.BaseIdEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
@Entity
@Table(name = "amenities")
public class Amenity extends BaseIdEntity {

  @Column(nullable = false)
  private String type;

  @OneToMany(mappedBy = "amenity", cascade = CascadeType.PERSIST)
  private final List<AccommodationAmenity> accommodationAmenities = new ArrayList<>();
}
