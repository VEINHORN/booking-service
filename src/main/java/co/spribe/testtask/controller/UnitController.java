package co.spribe.testtask.controller;

import co.spribe.testtask.model.entity.Unit;
import co.spribe.testtask.model.request.UnitRequest;
import co.spribe.testtask.model.response.UnitResponse;
import co.spribe.testtask.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/units")
public class UnitController {
    private final UnitService unitService;

    @GetMapping
    public Iterable<UnitResponse> getUnits(Pageable pageable) {
        return unitService.getAllUnits(pageable);
    }

    @PostMapping
    public void createUnit(@RequestBody UnitRequest request) {
        unitService.createUnit(request);
    }
}
