package com.hayan.hello_traveler.reservation.service;

import com.hayan.hello_traveler.accommodation.entity.Accommodation;
import com.hayan.hello_traveler.common.exception.CustomException;
import com.hayan.hello_traveler.common.response.ErrorCode;
import com.hayan.hello_traveler.reservation.entity.Reservation;
import com.hayan.hello_traveler.reservation.entity.Review;
import com.hayan.hello_traveler.reservation.entity.constant.Status;
import com.hayan.hello_traveler.reservation.entity.dto.ReviewRequest;
import com.hayan.hello_traveler.reservation.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

  private final ReservationService reservationService;

  private final ReviewRepository reviewRepository;

  @Transactional
  public Long save(Long guestId, Long reservationId, ReviewRequest request) {
    Reservation reservation = reservationService.getById(reservationId);
    reservationService.validateGuest(guestId, reservation);
    canWriteReview(reservation);

    Accommodation accommodation = reservation.getRoom().getAccommodation();
    Review review = request.toEntity(reservation, accommodation);
    reviewRepository.save(review);
    accommodation.addReview(request.starRating());

    return review.getId();
  }

  @Transactional
  public void patch(Long guestId, Long reviewId, ReviewRequest request) {
    Review review = getById(reviewId);
    reservationService.validateGuest(guestId, review.getReservation());

    Accommodation accommodation = review.getAccommodation();
    accommodation.updateReview(review.getStarRating(), request.starRating());

    review.update(request);
  }

  @Transactional
  public void delete(Long guestId, Long reviewId) {
    Review review = getById(reviewId);
    reservationService.validateGuest(guestId, review.getReservation());

    Accommodation accommodation = review.getAccommodation();
    accommodation.removeReview(review.getStarRating());

    reviewRepository.delete(review);
  }

  public Review getById(Long reviewId) {
    return reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
  }

  public void canWriteReview(Reservation reservation) {
    if (!reservation.getStatus().equals(Status.COMPLETED)) {
      throw new CustomException(ErrorCode.REVIEW_NOT_ALLOWED, "방문 완료 후에 리뷰 작성이 가능합니다.");
    }
    if (reviewRepository.existsByReservation(reservation)) {
      throw new CustomException(ErrorCode.EXISTS_REVIEW);
    }
  }
}
