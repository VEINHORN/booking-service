package com.veinhorn.booking.service.model.request;

import com.veinhorn.booking.service.model.entity.AccomodationType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UnitSearchRequest {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfFloors;
    private Integer floor;
    private BigDecimal cost;
    private String description;
    private AccomodationType accomodationType;
}
