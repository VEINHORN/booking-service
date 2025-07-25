package com.veinhorn.booking.service.repository;

import com.veinhorn.booking.service.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
