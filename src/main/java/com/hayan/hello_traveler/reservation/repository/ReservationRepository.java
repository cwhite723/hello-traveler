package com.hayan.hello_traveler.reservation.repository;

import com.hayan.hello_traveler.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
