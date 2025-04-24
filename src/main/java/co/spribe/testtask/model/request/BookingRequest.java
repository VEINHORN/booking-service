package co.spribe.testtask.model.request;

import java.time.LocalDate;
import java.util.UUID;

public record BookingRequest(
        UUID unitId,
        LocalDate checkInDate,
        LocalDate checkOutDate
) {}
