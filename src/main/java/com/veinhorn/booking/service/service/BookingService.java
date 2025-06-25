package com.veinhorn.booking.service.service;

import com.veinhorn.booking.service.exception.*;
import com.veinhorn.booking.service.model.entity.*;
import com.veinhorn.booking.service.model.request.BookingRequest;
import com.veinhorn.booking.service.model.response.BookingResponse;
import com.veinhorn.booking.service.repository.BookingRepository;
import com.veinhorn.booking.service.repository.UnitRepository;
import com.veinhorn.booking.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {
    private final UnitService unitService;
    private final UnitRepository unitRepository;
    private final BookingRepository bookingRepository;
    private final PaymentService paymentService;
    private final BookingAutoCanceller bookingAutoCanceller;
    private final UserRepository userRepository;
    private final EventService eventService;

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        if (request.checkOutDate().isBefore(request.checkInDate())) {
            throw new IncorrectDateRangeException();
        }

        var unit = unitRepository
                .findById(request.unitId())
                .orElseThrow(() -> new UnitNotExistException(request.unitId()));
        var user = userRepository
                .findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User %s not found".formatted(request.userId())));

        var isUnitAvailable = unitService.isUnitAvailable(unit, request.checkInDate(), request.checkOutDate());

        if (isUnitAvailable) {
            var newBooking = bookingRepository.save(createBooking(request, unit, user));

            paymentService.processPayment(newBooking);
            bookingAutoCanceller.schedulePaymentCheck(newBooking);
            eventService.storeEvent(EventType.BOOKING_CREATED, newBooking, user);

            return new BookingResponse(newBooking.getId());
        } else {
            throw new UnitIsNotAvailableException(request.unitId(), request.checkInDate(), request.checkOutDate());
        }
    }

    private Booking createBooking(BookingRequest request, Unit unit, User user) {
        var newBooking = new Booking();
        newBooking.setCheckInDate(request.checkInDate());
        newBooking.setCheckOutDate(request.checkOutDate());
        newBooking.setUnit(unit);
        newBooking.setStatus(BookingStatus.CREATED);
        newBooking.setUser(user);

        return newBooking;
    }

    @Transactional
    public void cancelBooking(UUID id) {
        bookingRepository
                .findById(id)
                .ifPresentOrElse(
                        booking -> {
                            booking.setStatus(BookingStatus.CANCELED);
                            eventService.storeEvent(EventType.BOOKING_CANCELLED, booking, booking.getUser());
                            booking
                                    .getPayments()
                                    .stream()
                                    .filter(payment -> PaymentStatus.PENDING.equals(payment.getStatus()))
                                    .forEach(payment -> payment.setStatus(PaymentStatus.EXPIRED));
                        },
                        () -> { throw new BookingNotExistException(); }
                );
    }
}
