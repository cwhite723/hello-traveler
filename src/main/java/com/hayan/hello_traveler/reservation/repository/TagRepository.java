package com.hayan.hello_traveler.reservation.repository;

import com.hayan.hello_traveler.reservation.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

}
