package com.hayan.hello_traveler.accommodation.entity.dto;

import com.hayan.hello_traveler.accommodation.entity.Accommodation;
import com.hayan.hello_traveler.accommodation.entity.Room;
import com.hayan.hello_traveler.user.domain.constant.Gender;

public record RoomRequest(String name,
                          Gender gender,
                          int capacity,
                          String description) {

  public Room toEntity(Accommodation accommodation) {
    return Room.builder()
        .name(name)
        .gender(gender)
        .capacity(capacity)
        .description(description)
        .accommodation(accommodation)
        .build();
  }
}
