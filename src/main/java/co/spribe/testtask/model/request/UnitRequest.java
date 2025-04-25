package co.spribe.testtask.model.request;

import co.spribe.testtask.model.entity.AccomodationType;

import java.math.BigDecimal;
import java.util.UUID;

public record UnitRequest(
        UUID userId,
        Integer numberOfRooms,
        AccomodationType accomodationType,
        Integer floor,
        BigDecimal cost,
        String description
) {}
