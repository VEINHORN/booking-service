package com.veinhorn.booking.service.model.request;

import com.veinhorn.booking.service.model.entity.AccomodationType;

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
