package com.hayan.hello_traveler.reservation.controller;

import static com.hayan.hello_traveler.common.response.SuccessCode.SUCCESS;

import com.hayan.hello_traveler.common.annotation.LoginCheck;
import com.hayan.hello_traveler.common.annotation.LoginGuestId;
import com.hayan.hello_traveler.common.annotation.LoginHostId;
import com.hayan.hello_traveler.common.response.ApplicationResponse;
import com.hayan.hello_traveler.reservation.entity.dto.ReservationRequest;
import com.hayan.hello_traveler.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @LoginCheck
  @PostMapping("/{room-id}")
  public ApplicationResponse<Long> create(@LoginGuestId Long guestId,
      @PathVariable("room-id") Long roomId,
      @RequestBody ReservationRequest request) {
    Long reservationId = reservationService.save(guestId, roomId, request);

    return ApplicationResponse.ok(reservationId, SUCCESS);
  }

  @LoginCheck
  @PatchMapping("/approve/{reservation-id}")
  public ApplicationResponse<Void> approve(@LoginHostId Long hostId,
      @PathVariable("reservation-id") Long reservationId) {
    reservationService.approve(hostId, reservationId);

    return ApplicationResponse.noData(SUCCESS);
  }

  @LoginCheck
  @PatchMapping("/cancel/{reservation-id}")
  public ApplicationResponse<Void> cancel(@LoginGuestId Long guestId,
      @PathVariable("reservation-id") Long reservationId) {
    reservationService.cancel(guestId, reservationId);

    return ApplicationResponse.noData(SUCCESS);
  }

  @LoginCheck
  @PatchMapping("/reject/{reservation-id}")
  public ApplicationResponse<Void> reject(@LoginHostId Long hostId,
      @PathVariable("reservation-id") Long reservationId) {
    reservationService.reject(hostId, reservationId);

    return ApplicationResponse.noData(SUCCESS);
  }
}
