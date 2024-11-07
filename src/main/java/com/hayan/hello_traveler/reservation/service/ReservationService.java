package com.hayan.hello_traveler.reservation.service;

import com.hayan.hello_traveler.accommodation.entity.Room;
import com.hayan.hello_traveler.accommodation.service.RoomService;
import com.hayan.hello_traveler.common.exception.CustomException;
import com.hayan.hello_traveler.common.response.ErrorCode;
import com.hayan.hello_traveler.reservation.entity.Reservation;
import com.hayan.hello_traveler.reservation.entity.dto.ReservationRequest;
import com.hayan.hello_traveler.reservation.repository.ReservationRepository;
import com.hayan.hello_traveler.user.domain.Guest;
import com.hayan.hello_traveler.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

  private final UserService userService;
  private final RoomService roomService;

  private final ReservationRepository reservationRepository;

  @Transactional
  public Long save(Long guestId, Long roomId, ReservationRequest request) {
    Guest guest = (Guest) userService.getById(guestId);
    Room room = roomService.getById(roomId);
    roomService.checkAvailability(room, request.checkinDate(), request.checkoutDate());

    Reservation reservation = request.toEntity(room.getAccommodation().getHost(), guest, room);
    reservationRepository.save(reservation);

    return reservation.getId();
  }

  @Transactional
  public void approve(Long hostId, Long reservationId) {
    Reservation reservation = getById(reservationId);

    validateHost(hostId, reservation);
    reservation.approve();
  }

  @Transactional
  public void cancel(Long guestId, Long reservationId) {
    Reservation reservation = getById(reservationId);

    validateGuest(guestId, reservation);
    reservation.cancel();
  }

  @Transactional
  public void reject(Long hostId, Long reservationId) {
    Reservation reservation = getById(reservationId);

    validateHost(hostId, reservation);
    reservation.reject();
  }

  public Reservation getById(Long reservationId) {
    return reservationRepository.findById(reservationId)
        .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
  }

  public void validateHost(Long hostId, Reservation reservation) {
    if (!reservation.getHost().getId().equals(hostId)) {
      throw new CustomException(ErrorCode.HOST_NOT_MATCH);
    }
  }

  public void validateGuest(Long guestId, Reservation reservation) {
    if (!reservation.getGuest().getId().equals(guestId)) {
      throw new CustomException(ErrorCode.GUEST_NOT_MATCH);
    }
  }
}
