package com.veinhorn.booking.service.exception;

import java.time.LocalDate;
import java.util.UUID;

public class UnitIsNotAvailableException extends RuntimeException {
    public UnitIsNotAvailableException(UUID unitId, LocalDate checkInDate, LocalDate checkOutDate) {
        super("Unit %s is not available for booking from %s to %s".formatted(unitId, checkInDate, checkOutDate));
    }
}
