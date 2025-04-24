package co.spribe.testtask.service;

import co.spribe.testtask.model.entity.Unit;
import co.spribe.testtask.model.request.UnitRequest;
import co.spribe.testtask.model.response.UnitResponse;
import co.spribe.testtask.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;

    public Page<UnitResponse> getAllUnits(Pageable pageable) {
        return unitRepository
                .findAll(pageable)
                .map(this::convert);
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

    private UnitResponse convert(Unit unit) {
        boolean isUnitAvailable = true;

        return new UnitResponse(
                unit.getId(),
                unit.getNumberOfRooms(),
                unit.getFloor(),
                unit.getDescription(),
                unit.getAccomodationType(),
                unit.getCost(),
                isUnitAvailable
        );
    }
}
