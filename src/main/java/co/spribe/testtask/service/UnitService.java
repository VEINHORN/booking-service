package co.spribe.testtask.service;

import co.spribe.testtask.model.entity.Unit;
import co.spribe.testtask.model.request.UnitRequest;
import co.spribe.testtask.model.response.UnitResponse;
import co.spribe.testtask.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;

    public Page<UnitResponse> getAllUnits(Pageable pageable) {
        return unitRepository
                .findAll(pageable)
                .map(unit -> convert(unit, null, null));
    }

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
        var allUnitBookings = unit.getBookings();

        return allUnitBookings
                .stream()
                .allMatch(booking -> {
                    return checkOutDate.isBefore(booking.getCheckInDate()) || booking.getCheckOutDate().isBefore(checkInDate);
                });
    }

    private UnitResponse convert(Unit unit, LocalDate checkInDate, LocalDate checkOutDate) {
        return new UnitResponse(
                unit.getId(),
                unit.getNumberOfRooms(),
                unit.getFloor(),
                unit.getDescription(),
                unit.getAccomodationType(),
                unit.getCost(),
                checkInDate == null || checkOutDate == null || isUnitAvailable(unit, checkInDate, checkOutDate)
        );
    }
}
