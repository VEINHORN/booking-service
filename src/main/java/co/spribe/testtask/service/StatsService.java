package co.spribe.testtask.service;

import co.spribe.testtask.model.response.AvailableUnitResponse;
import co.spribe.testtask.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StatsService {
    private static final int AVAILABLE_UNITS_KEY = 0;

    private final Map<Integer, AtomicLong> stats;
    private UnitRepository unitRepository;

    public StatsService(UnitRepository unitRepository) {
        stats = new ConcurrentHashMap<>();
        this.unitRepository = unitRepository;
    }

    public void incrementAvailableUnits() {
        stats.compute(AVAILABLE_UNITS_KEY, (key, value) -> {
            return value == null ? new AtomicLong(unitRepository.count()) : new AtomicLong(value.incrementAndGet());
        });
    }

    public AvailableUnitResponse getAvailableUnits() {
        return new AvailableUnitResponse(
                stats.computeIfAbsent(AVAILABLE_UNITS_KEY, key -> new AtomicLong(unitRepository.count()))
        );
    }
}
