package com.veinhorn.booking.service.repository;

import com.veinhorn.booking.service.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
}
