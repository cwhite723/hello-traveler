package com.hayan.hello_traveler.accommodation.repository;

import com.hayan.hello_traveler.accommodation.entity.DailyRoomStock;
import com.hayan.hello_traveler.accommodation.entity.Room;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyRoomStockRepository extends JpaRepository<DailyRoomStock, Long> {

  Optional<DailyRoomStock> findByRoomAndDate(Room room, LocalDate date);
}
