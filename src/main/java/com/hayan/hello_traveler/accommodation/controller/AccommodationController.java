package com.hayan.hello_traveler.accommodation.controller;

import static com.hayan.hello_traveler.common.response.SuccessCode.SUCCESS;

import com.hayan.hello_traveler.accommodation.entity.dto.AccommodationRequest;
import com.hayan.hello_traveler.accommodation.service.AccommodationService;
import com.hayan.hello_traveler.common.annotation.LoginCheck;
import com.hayan.hello_traveler.common.annotation.LoginHostId;
import com.hayan.hello_traveler.common.response.ApplicationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accommodation")
@RequiredArgsConstructor
public class AccommodationController {

  private final AccommodationService accommodationService;

  @LoginCheck
  @PostMapping
  public ApplicationResponse<Long> create(@LoginHostId Long hostId,
      @RequestBody AccommodationRequest request) {
    Long accommodationId = accommodationService.save(hostId, request);

    return ApplicationResponse.ok(accommodationId, SUCCESS);
  }

  @LoginCheck
  @PostMapping("/{accommodation-id}")
  public ApplicationResponse<Void> createAmenities(@LoginHostId Long hostId,
      @PathVariable("accommodation-id") Long accommodationId,
      @RequestBody List<Long> amenityIds) {
    accommodationService.saveAmenities(hostId, accommodationId, amenityIds);

    return ApplicationResponse.noData(SUCCESS);
  }

  @LoginCheck
  @PatchMapping("/{accommodation-id}")
  public ApplicationResponse<Void> update(@LoginHostId Long hostId,
      @PathVariable("accommodation-id") Long accommodationId,
      @RequestBody AccommodationRequest request) {
    accommodationService.patch(hostId, accommodationId, request);

    return ApplicationResponse.noData(SUCCESS);
  }

  @LoginCheck
  @DeleteMapping("/{accommodation-id}")
  public ApplicationResponse<Void> delete(@LoginHostId Long hostId,
      @PathVariable("accommodation-id") Long accommodationId) {
    accommodationService.delete(hostId, accommodationId);

    return ApplicationResponse.noData(SUCCESS);
  }
}
