package co.spribe.testtask.exception;

import java.util.UUID;

public class UnitIsNotAvailable extends RuntimeException {
    public UnitIsNotAvailable(UUID unitId) {
        super("Unit %s is not available for booking".formatted(unitId));
    }
}
