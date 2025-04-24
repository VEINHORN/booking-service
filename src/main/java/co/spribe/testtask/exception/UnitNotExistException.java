package co.spribe.testtask.exception;

import java.util.UUID;

public class UnitNotExistException extends RuntimeException {
    public UnitNotExistException(UUID unitId) {
        super("%s unit does not exist in the system".formatted(unitId));
    }
}
