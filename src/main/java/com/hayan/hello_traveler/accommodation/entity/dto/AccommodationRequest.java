package com.hayan.hello_traveler.accommodation.entity.dto;

import com.hayan.hello_traveler.accommodation.entity.Accommodation;
import com.hayan.hello_traveler.accommodation.entity.constant.Type;
import com.hayan.hello_traveler.user.domain.Host;
import java.time.LocalTime;

public record AccommodationRequest(String name, String contact, String address, String city,
                                   String district,
                                   double latitude, double longitude, Type type,
                                   String description,
                                   LocalTime checkinTime, LocalTime checkoutTime) {

  public Accommodation toEntity(Host host) {
    return Accommodation.builder()
        .name(name)
        .contact(contact)
        .address(address)
        .city(city)
        .district(district)
        .latitude(latitude)
        .longitude(longitude)
        .type(type)
        .description(description)
        .checkinTime(checkinTime)
        .checkoutTime(checkoutTime)
        .host(host)
        .build();
  }
}
