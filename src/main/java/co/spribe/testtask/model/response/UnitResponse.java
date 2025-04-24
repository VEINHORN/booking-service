package co.spribe.testtask.model.response;

import co.spribe.testtask.model.entity.AccomodationType;

import java.math.BigDecimal;
import java.util.UUID;

public record UnitResponse(
        UUID id,
        Integer numberOfFloors,
        Integer floor,
        String description,
        AccomodationType accomodationType,
        BigDecimal cost,
        Boolean available) {
}
