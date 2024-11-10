package com.hayan.hello_traveler.accommodation.repository;

import com.hayan.hello_traveler.accommodation.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

}
