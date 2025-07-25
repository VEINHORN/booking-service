package com.veinhorn.booking.service.controller;

import com.veinhorn.booking.service.model.entity.AccomodationType;
import com.veinhorn.booking.service.model.request.UnitRequest;
import com.veinhorn.booking.service.model.request.UnitSearchRequest;
import com.veinhorn.booking.service.model.response.AvailableUnitResponse;
import com.veinhorn.booking.service.model.response.UnitResponse;
import com.veinhorn.booking.service.service.StatsService;
import com.veinhorn.booking.service.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/units")
public class UnitController {
    private final UnitService unitService;
    private final StatsService statsService;

    @GetMapping
    @Operation(summary = "Shows units with filtering capabilities")
    public Iterable<UnitResponse> searchUnits(
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam(required = false) Integer numberOfFloors,
            @RequestParam(required = false) Integer floor,
            @RequestParam(required = false) BigDecimal cost,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) AccomodationType accomodationType,
            Pageable pageable) {
        var searchRequest = new UnitSearchRequest();
        searchRequest.setCheckInDate(checkInDate);
        searchRequest.setCheckOutDate(checkOutDate);
        searchRequest.setNumberOfFloors(numberOfFloors);
        searchRequest.setFloor(floor);
        searchRequest.setCost(cost);
        searchRequest.setDescription(description);
        searchRequest.setAccomodationType(accomodationType);

        return unitService.searchUnits(searchRequest, pageable);
    }

    @GetMapping("/available")
    @Operation(summary = "Shows stats about available units")
    public AvailableUnitResponse getAvailableUnitsStats() {
        return statsService.getAvailableUnits();
    }

    @PostMapping
    @Operation(summary = "Creates a new unit")
    public void createUnit(@RequestBody UnitRequest request) {
        unitService.createUnit(request);
    }
}
