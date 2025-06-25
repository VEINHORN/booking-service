package com.veinhorn.booking.service.model.request;

import java.time.LocalDate;
import java.util.UUID;

public record BookingRequest(
        UUID userId,
        UUID unitId,
        LocalDate checkInDate,
        LocalDate checkOutDate
) {}
