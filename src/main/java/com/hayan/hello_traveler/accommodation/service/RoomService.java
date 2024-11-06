package com.hayan.hello_traveler.accommodation.service;

import com.hayan.hello_traveler.accommodation.entity.Accommodation;
import com.hayan.hello_traveler.accommodation.entity.Room;
import com.hayan.hello_traveler.accommodation.entity.dto.RoomRequest;
import com.hayan.hello_traveler.accommodation.repository.RoomRepository;
import com.hayan.hello_traveler.common.exception.CustomException;
import com.hayan.hello_traveler.common.response.ErrorCode;
import com.hayan.hello_traveler.user.domain.Host;
import com.hayan.hello_traveler.user.service.UserService;
import com.hayan.hello_traveler.user.service.factory.HostFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

  private final UserService userService;
  private final AccommodationService accommodationService;

  private final RoomRepository roomRepository;
  private final HostFactory host;

  @Transactional
  public Long save(Long hostId, Long accommodationId, RoomRequest request) {
    Accommodation accommodation = accommodationService.getById(accommodationId);
    accommodationService.validateHost(hostId, accommodation);

    Room room = request.toEntity(accommodation);
    roomRepository.save(room);

    return room.getId();
  }

  @Transactional
  public void patch(Long hostId, Long roomId, RoomRequest request) {
    Room room = getById(roomId);
    accommodationService.validateHost(hostId, room.getAccommodation());

    room.update(request);
  }

  public void delete(Long hostId, Long roomId) {
    Room room = getById(roomId);
    accommodationService.validateHost(hostId, room.getAccommodation());

    roomRepository.delete(room);
  }

  public Room getById(Long roomId) {
    return roomRepository.findById(roomId)
        .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
  }
}
