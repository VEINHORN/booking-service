package co.spribe.testtask.model.response;

import java.util.concurrent.atomic.AtomicLong;

public record AvailableUnitResponse(AtomicLong availableUnitsCount) {
}
