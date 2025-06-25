package com.veinhorn.booking.service.service;

import com.veinhorn.booking.service.exception.IncorrectDateRangeException;
import com.veinhorn.booking.service.exception.ResourceNotFoundException;
import com.veinhorn.booking.service.model.entity.BookingStatus;
import com.veinhorn.booking.service.model.entity.Unit;
import com.veinhorn.booking.service.model.request.UnitRequest;
import com.veinhorn.booking.service.model.request.UnitSearchRequest;
import com.veinhorn.booking.service.model.response.UnitResponse;
import com.veinhorn.booking.service.repository.UnitRepository;
import com.veinhorn.booking.service.repository.UnitSpecifications;
import com.veinhorn.booking.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final StatsService statsService;

    public Page<UnitResponse> searchUnits(UnitSearchRequest request, Pageable pageable) {
        if (request.getCheckOutDate().isBefore(request.getCheckInDate())) {
            throw new IncorrectDateRangeException();
        }

        return unitRepository
                .findAll(createSpecification(request), pageable)
                .map(unit -> toUnitResponse(unit, request.getCheckInDate(), request.getCheckOutDate()));
    }

    private Specification<Unit> createSpecification(UnitSearchRequest request) {
        return UnitSpecifications
                .hasNumberOfRooms(request.getNumberOfFloors())
                .and(UnitSpecifications.hasCost(request.getCost()))
                .and(UnitSpecifications.likeDescription(request.getDescription()))
                .and(UnitSpecifications.hasAccomodationType(request.getAccomodationType()));
    }

    @Transactional
    public void createUnit(UnitRequest request) {
        var user = userRepository
                .findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User %s not found".formatted(request.userId())));

        var newUnit = new Unit();
        newUnit.setNumberOfRooms(request.numberOfRooms());
        newUnit.setFloor(request.floor());
        newUnit.setDescription(request.description());
        newUnit.setAccomodationType(request.accomodationType());
        newUnit.setCost(request.cost());
        newUnit.setUser(user);

        unitRepository.save(newUnit);
        statsService.incrementAvailableUnits();
    }

    public boolean isUnitAvailable(Unit unit, LocalDate checkInDate, LocalDate checkOutDate) {
        return unit
                .getBookings()
                .stream()
                .filter(booking -> !BookingStatus.CANCELED.equals(booking.getStatus()))
                .allMatch(booking -> {
                    return checkOutDate.isBefore(booking.getCheckInDate()) || booking.getCheckOutDate().isBefore(checkInDate);
                });
    }

    private UnitResponse toUnitResponse(Unit unit, @NonNull LocalDate checkInDate, @NonNull LocalDate checkOutDate) {
        return new UnitResponse(
                unit.getId(),
                unit.getNumberOfRooms(),
                unit.getFloor(),
                unit.getDescription(),
                unit.getAccomodationType(),
                unit.getCost(),
                isUnitAvailable(unit, checkInDate, checkOutDate)
        );
    }
}
