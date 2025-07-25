package com.veinhorn.booking.service.service;

import com.veinhorn.booking.service.model.response.AvailableUnitResponse;
import com.veinhorn.booking.service.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class StatsService {
    private static final int AVAILABLE_UNITS_KEY = 0;

    private final Map<Integer, AtomicLong> stats = new ConcurrentHashMap<>();
    private final UnitRepository unitRepository;

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
