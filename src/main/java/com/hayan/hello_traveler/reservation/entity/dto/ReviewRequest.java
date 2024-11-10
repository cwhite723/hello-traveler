package com.hayan.hello_traveler.reservation.entity.dto;

import com.hayan.hello_traveler.accommodation.entity.Accommodation;
import com.hayan.hello_traveler.reservation.entity.Reservation;
import com.hayan.hello_traveler.reservation.entity.Review;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;

public record ReviewRequest(String content,

                            @Min(1) @Max(5)
                            int starRating,

                            @Nullable
                            String comment,

                            List<Long> tagIds) {

  public Review toEntity(Reservation reservation, Accommodation accommodation) {
    return new Review(content, starRating, comment, reservation, accommodation);
  }
}
