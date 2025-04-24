package co.spribe.testtask.exception;

import java.time.LocalDate;
import java.util.UUID;

public class UnitIsNotAvailable extends RuntimeException {
    public UnitIsNotAvailable(UUID unitId, LocalDate checkInDate, LocalDate checkOutDate) {
        super("Unit %s is not available for booking from %s to %s".formatted(unitId, checkInDate, checkOutDate));
    }
}
