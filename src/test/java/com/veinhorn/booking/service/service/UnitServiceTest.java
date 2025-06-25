package com.veinhorn.booking.service.service;

import com.veinhorn.booking.service.exception.ResourceNotFoundException;
import com.veinhorn.booking.service.model.entity.AccomodationType;
import com.veinhorn.booking.service.model.entity.Unit;
import com.veinhorn.booking.service.model.entity.User;
import com.veinhorn.booking.service.model.request.UnitRequest;
import com.veinhorn.booking.service.model.request.UnitSearchRequest;
import com.veinhorn.booking.service.repository.UnitRepository;
import com.veinhorn.booking.service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {
    @InjectMocks
    private UnitService unitService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private StatsService statsService;

    @Test
    void testSearchingUnits() {
        var request = new UnitSearchRequest();
        request.setCheckInDate(LocalDate.now().minusDays(5));
        request.setCheckOutDate(LocalDate.now().plusDays(5));

        var flatUnit = new Unit();
        flatUnit.setId(UUID.randomUUID());
        flatUnit.setAccomodationType(AccomodationType.FLAT);

        var homeUnit = new Unit();
        homeUnit.setId(UUID.randomUUID());
        homeUnit.setAccomodationType(AccomodationType.HOME);

        var units = List.of(flatUnit, homeUnit);
        when(unitRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl(units));

        var result = unitService.searchUnits(request, Pageable.unpaged());
        assertThat(result.getTotalElements()).isEqualTo(2);
    }

    @Test
    void testCreatingUnit() {
        var request = new UnitRequest(UUID.randomUUID(), 3, AccomodationType.FLAT, 3, BigDecimal.valueOf(10), "Flat 1");

        var user = new User();
        user.setId(UUID.randomUUID());
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        unitService.createUnit(request);

        verify(unitRepository, times(1)).save(any());
        verify(statsService, times(1)).incrementAvailableUnits();
    }

    @Test
    void testCreatingUnitWithMissingUserIdShouldThrowException() {
        var request = new UnitRequest(UUID.randomUUID(), 3, AccomodationType.FLAT, 3, BigDecimal.valueOf(10), "Flat 1");

        assertThatThrownBy(() -> unitService.createUnit(request))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}