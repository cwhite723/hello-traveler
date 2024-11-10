package com.hayan.hello_traveler.accommodation.repository;

import com.hayan.hello_traveler.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

}
