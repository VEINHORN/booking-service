package co.spribe.testtask.exception;

import java.util.UUID;

public class UnitDoesNotExist extends RuntimeException {
    public UnitDoesNotExist(UUID unitId) {
        super("%s unit does not exist in the system".formatted(unitId));
    }
}
