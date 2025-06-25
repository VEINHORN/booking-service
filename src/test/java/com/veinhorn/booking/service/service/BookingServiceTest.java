package com.veinhorn.booking.service.service;

import com.veinhorn.booking.service.exception.IncorrectDateRangeException;
import com.veinhorn.booking.service.exception.ResourceNotFoundException;
import com.veinhorn.booking.service.exception.UnitNotExistException;
import com.veinhorn.booking.service.model.entity.Booking;
import com.veinhorn.booking.service.model.entity.Unit;
import com.veinhorn.booking.service.model.entity.User;
import com.veinhorn.booking.service.model.request.BookingRequest;
import com.veinhorn.booking.service.repository.BookingRepository;
import com.veinhorn.booking.service.repository.UnitRepository;
import com.veinhorn.booking.service.repository.UserRepository;
import com.veinhorn.booking.service.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UnitService unitService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private BookingAutoCanceller bookingAutoCanceller;

    @Mock
    private EventService eventService;

    @Test
    void testCreatingBooking() {
        var request = new BookingRequest(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), LocalDate.now());

        var user = new User();
        user.setId(request.userId());
        when(userRepository.findById(request.userId())).thenReturn(Optional.of(user));

        var unit = new Unit();
        unit.setId(request.unitId());
        when(unitRepository.findById(request.unitId())).thenReturn(Optional.of(unit));

        when(unitService.isUnitAvailable(any(), any(), any())).thenReturn(true);

        var newBooking = new Booking();
        newBooking.setId(UUID.randomUUID());
        when(bookingRepository.save(any())).thenReturn(newBooking);

        var booking = bookingService.createBooking(request);

        assertThat(booking.id()).isNotNull();
        assertThat(newBooking.getId()).isEqualTo(booking.id());

        verify(paymentService, times(1)).processPayment(any());
        verify(bookingAutoCanceller, times(1)).schedulePaymentCheck(any());
        verify(eventService, times(1)).storeEvent(any(), any(), any());
    }

    @Test
    void bookingWithIncorrectDateRangeShouldThrowException() {
        var request = new BookingRequest(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), LocalDate.now().minusDays(1));

        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(IncorrectDateRangeException.class);
    }

    @Test
    void bookingWithMissingUnitIdShouldThrowException() {
        var request = new BookingRequest(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), LocalDate.now());

        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(UnitNotExistException.class);
    }

    @Test
    void bookingWithMissingUserIdShouldThrowException() {
        var request = new BookingRequest(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), LocalDate.now());

        when(unitRepository.findById(any())).thenReturn(Optional.of(new Unit()));

        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}