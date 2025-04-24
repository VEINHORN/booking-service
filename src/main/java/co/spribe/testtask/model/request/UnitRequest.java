package co.spribe.testtask.model.request;

import co.spribe.testtask.model.entity.AccomodationType;

import java.math.BigDecimal;

public record UnitRequest(
        Integer numberOfRooms,
        AccomodationType accomodationType,
        Integer floor,
        BigDecimal cost,
        String description
) {}
