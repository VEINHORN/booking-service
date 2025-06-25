package com.veinhorn.booking.service.model.response;

import java.util.concurrent.atomic.AtomicLong;

public record AvailableUnitResponse(AtomicLong availableUnitsCount) {
}
