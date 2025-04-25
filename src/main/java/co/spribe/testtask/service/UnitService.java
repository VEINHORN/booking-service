package co.spribe.testtask.service;

import co.spribe.testtask.exception.IncorrectDateRangeException;
import co.spribe.testtask.model.entity.Unit;
import co.spribe.testtask.model.request.UnitRequest;
import co.spribe.testtask.model.request.UnitSearchRequest;
import co.spribe.testtask.model.response.UnitResponse;
import co.spribe.testtask.repository.UnitRepository;
import co.spribe.testtask.repository.UnitSpecifications;
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
    private final UnitRepository unitRepository;

    public Page<UnitResponse> searchUnits(UnitSearchRequest request, Pageable pageable) {
        if (request.getCheckOutDate().isBefore(request.getCheckInDate())) {
            throw new IncorrectDateRangeException();
        }

        var spec = Specification.where(
                UnitSpecifications.hasNumberOfRooms(request.getNumberOfFloors())
                        .and(UnitSpecifications.hasCost(request.getCost()))
                        .and(UnitSpecifications.likeDescription(request.getDescription()))
                        .and(UnitSpecifications.hasAccomodationType(request.getAccomodationType()))
        );

        return unitRepository
                .findAll(spec, pageable)
                .map(unit -> toUnitResponse(unit, request.getCheckInDate(), request.getCheckOutDate()));
    }

    @Transactional
    public void createUnit(UnitRequest request) {
        var newUnit = new Unit();
        newUnit.setNumberOfRooms(request.numberOfRooms());
        newUnit.setFloor(request.floor());
        newUnit.setDescription(request.description());
        newUnit.setAccomodationType(request.accomodationType());
        newUnit.setCost(request.cost());

        unitRepository.save(newUnit);
    }

    public boolean isUnitAvailable(Unit unit, LocalDate checkInDate, LocalDate checkOutDate) {
        return unit
                .getBookings()
                .stream()
                .filter(booking -> !booking.getCancelled())
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
