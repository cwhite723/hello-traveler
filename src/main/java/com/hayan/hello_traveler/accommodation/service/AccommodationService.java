package com.hayan.hello_traveler.accommodation.service;

import com.hayan.hello_traveler.accommodation.entity.Accommodation;
import com.hayan.hello_traveler.accommodation.entity.AccommodationAmenity;
import com.hayan.hello_traveler.accommodation.entity.Amenity;
import com.hayan.hello_traveler.accommodation.entity.Bookmark;
import com.hayan.hello_traveler.accommodation.entity.dto.AccommodationRequest;
import com.hayan.hello_traveler.accommodation.repository.AccommodationAmenityRepository;
import com.hayan.hello_traveler.accommodation.repository.AccommodationRepository;
import com.hayan.hello_traveler.accommodation.repository.AmenityRepository;
import com.hayan.hello_traveler.accommodation.repository.BookmarkRepository;
import com.hayan.hello_traveler.common.exception.CustomException;
import com.hayan.hello_traveler.common.response.ErrorCode;
import com.hayan.hello_traveler.user.domain.Guest;
import com.hayan.hello_traveler.user.domain.Host;
import com.hayan.hello_traveler.user.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccommodationService {

  private final UserService userService;

  private final AccommodationRepository accommodationRepository;
  private final AmenityRepository amenityRepository;
  private final AccommodationAmenityRepository accommodationAmenityRepository;
  private final BookmarkRepository bookmarkRepository;

  @Transactional
  public Long save(Long hostId, AccommodationRequest request) {
    Host host = (Host) userService.getById(hostId);
    Accommodation accommodation = request.toEntity(host);

    accommodationRepository.save(accommodation);

    return accommodation.getId();
  }

  @Transactional
  public void saveAmenities(Long hostId, Long accommodationId, List<Long> amenityIds) {
    Accommodation accommodation = getById(accommodationId);
    validateHost(hostId, accommodation);

    List<Amenity> amenities = amenityRepository.findAllById(amenityIds);
    if (amenities.size() != amenityIds.size()) {
      throw new CustomException(ErrorCode.AMENITY_NOT_FOUND);
    }

    List<AccommodationAmenity> accommodationAmenities = amenities.stream()
        .map(amenity -> new AccommodationAmenity(accommodation, amenity))
        .collect(Collectors.toList());

    accommodationAmenityRepository.saveAll(accommodationAmenities);
  }

  @Transactional
  public void patch(Long hostId, Long accommodationId, AccommodationRequest request) {
    Accommodation accommodation = getById(accommodationId);
    validateHost(hostId, accommodation);
    accommodation.update(request);
  }

  @Transactional
  public void delete(Long hostId, Long accommodationId) {
    Accommodation accommodation = getById(accommodationId);
    validateHost(hostId, accommodation);
    accommodation.delete();
  }

  @Transactional
  public void toggleBookmark(Long guestId, Long accommodationId) {
    Guest guest = (Guest) userService.getById(guestId);
    Accommodation accommodation = getById(accommodationId);

    bookmarkRepository.findByGuestAndAccommodation(guest, accommodation)
        .ifPresentOrElse(
            bookmarkRepository::delete,
            () -> bookmarkRepository.save(new Bookmark(guest, accommodation))
        );
  }

  public Accommodation getById(Long accommodationId) {
    return accommodationRepository.findById(accommodationId)
        .orElseThrow(() -> new CustomException(ErrorCode.ACCOMMODATION_NOT_FOUND));
  }

  public void validateHost(Long loginId, Accommodation accommodation) {
    if (!accommodation.getHost().getId().equals(loginId)) {
      throw new CustomException(ErrorCode.HOST_NOT_MATCH);
    }
  }
}
