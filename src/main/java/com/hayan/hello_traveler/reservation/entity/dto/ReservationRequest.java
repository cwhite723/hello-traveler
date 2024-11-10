package com.hayan.hello_traveler.reservation.entity.dto;

import com.hayan.hello_traveler.accommodation.entity.Room;
import com.hayan.hello_traveler.reservation.entity.Reservation;
import com.hayan.hello_traveler.reservation.entity.constant.Status;
import com.hayan.hello_traveler.user.domain.Guest;
import com.hayan.hello_traveler.user.domain.Host;
import java.time.LocalDate;

public record ReservationRequest(LocalDate checkinDate,
                                 LocalDate checkoutDate) {

  public ReservationRequest {
    if (!checkinDate.isBefore(checkoutDate)) {
      throw new IllegalArgumentException("체크인 날짜는 체크아웃 날짜 이전이어야 합니다.");
    }
  }

  public Reservation toEntity(Host host, Guest guest, Room room) {
    return Reservation.builder()
        .checkinDate(checkinDate)
        .checkoutDate(checkoutDate)
        .status(Status.REQUESTED)
        .host(host)
        .guest(guest)
        .room(room)
        .build();
  }
}
