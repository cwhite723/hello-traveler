package com.hayan.hello_traveler.accommodation.service;

import com.hayan.hello_traveler.accommodation.entity.Accommodation;
import com.hayan.hello_traveler.accommodation.entity.DailyRoomStock;
import com.hayan.hello_traveler.accommodation.entity.Room;
import com.hayan.hello_traveler.accommodation.entity.dto.RoomRequest;
import com.hayan.hello_traveler.accommodation.repository.DailyRoomStockRepository;
import com.hayan.hello_traveler.accommodation.repository.RoomRepository;
import com.hayan.hello_traveler.common.exception.CustomException;
import com.hayan.hello_traveler.common.response.ErrorCode;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

  private final AccommodationService accommodationService;

  private final RoomRepository roomRepository;
  private final DailyRoomStockRepository dailyRoomStockRepository;

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

  @Transactional
  public void delete(Long hostId, Long roomId) {
    Room room = getById(roomId);
    accommodationService.validateHost(hostId, room.getAccommodation());

    roomRepository.delete(room);
  }

  public Room getById(Long roomId) {
    return roomRepository.findById(roomId)
        .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
  }

  @Transactional
  public void checkAvailability(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
    LocalDate date = checkInDate;

    while (!date.isEqual(checkOutDate)) {
      LocalDate currentDate = date;
      DailyRoomStock dailyRoomStock = dailyRoomStockRepository.findByRoomAndDate(room, currentDate)
          .orElseGet(() -> createDailyRoomStock(room, currentDate));

      if (dailyRoomStock.getAvailableCount() == 0) {
        throw new CustomException(ErrorCode.RESERVATION_CLOSED);
      }

      date = date.plusDays(1);
    }
  }

  private DailyRoomStock createDailyRoomStock(Room room, LocalDate date) {
    DailyRoomStock dailyRoomStock = new DailyRoomStock(date, room.getCapacity(), room);
    return dailyRoomStockRepository.save(dailyRoomStock);
  }
}
