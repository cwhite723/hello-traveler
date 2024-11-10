package com.hayan.hello_traveler.reservation.controller;

import static com.hayan.hello_traveler.common.response.SuccessCode.SUCCESS;

import com.hayan.hello_traveler.common.annotation.LoginCheck;
import com.hayan.hello_traveler.common.annotation.LoginGuestId;
import com.hayan.hello_traveler.common.response.ApplicationResponse;
import com.hayan.hello_traveler.common.response.SuccessCode;
import com.hayan.hello_traveler.reservation.entity.dto.ReviewRequest;
import com.hayan.hello_traveler.reservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  @LoginCheck
  @PostMapping("/{reservation-id}")
  public ApplicationResponse<Long> create(@LoginGuestId Long guestId,
      @PathVariable("reservation-id") Long reservationId,
      @RequestBody ReviewRequest request) {
    Long reviewId = reviewService.save(guestId, reservationId, request);

    return ApplicationResponse.ok(reviewId, SUCCESS);
  }

  @LoginCheck
  @PatchMapping("/{review-id}")
  public ApplicationResponse<Void> update(@LoginGuestId Long guestId,
      @PathVariable("review-id") Long reviewId,
      @RequestBody ReviewRequest request) {
    reviewService.patch(guestId, reviewId, request);

    return ApplicationResponse.noData(SUCCESS);
  }

  @LoginCheck
  @DeleteMapping("/{review-id}")
  public ApplicationResponse<Void> delete(@LoginGuestId Long guestId,
      @PathVariable("review-id") Long reviewId) {
    reviewService.delete(guestId, reviewId);

    return ApplicationResponse.noData(SUCCESS);
  }
}
