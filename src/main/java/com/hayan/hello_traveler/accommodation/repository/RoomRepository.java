package com.hayan.hello_traveler.accommodation.repository;

import com.hayan.hello_traveler.accommodation.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
