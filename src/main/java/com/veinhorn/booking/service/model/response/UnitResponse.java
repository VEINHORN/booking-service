package com.veinhorn.booking.service.model.response;

import com.veinhorn.booking.service.model.entity.AccomodationType;

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
