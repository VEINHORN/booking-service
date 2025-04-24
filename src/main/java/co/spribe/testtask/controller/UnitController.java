package co.spribe.testtask.controller;

import co.spribe.testtask.model.entity.AccomodationType;
import co.spribe.testtask.model.request.UnitRequest;
import co.spribe.testtask.model.request.UnitSearchRequest;
import co.spribe.testtask.model.response.UnitResponse;
import co.spribe.testtask.service.UnitService;
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

    @GetMapping
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

    @PostMapping
    public void createUnit(@RequestBody UnitRequest request) {
        unitService.createUnit(request);
    }
}
