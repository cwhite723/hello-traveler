package com.hayan.hello_traveler.accommodation.repository;

import com.hayan.hello_traveler.accommodation.entity.Accommodation;
import com.hayan.hello_traveler.accommodation.entity.Bookmark;
import com.hayan.hello_traveler.user.domain.Guest;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

  Optional<Bookmark> findByGuestAndAccommodation(Guest guest, Accommodation accommodation);
}
