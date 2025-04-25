package co.spribe.testtask.service;

import co.spribe.testtask.exception.*;
import co.spribe.testtask.model.entity.Booking;
import co.spribe.testtask.model.entity.PaymentStatus;
import co.spribe.testtask.model.entity.Unit;
import co.spribe.testtask.model.entity.User;
import co.spribe.testtask.model.request.BookingRequest;
import co.spribe.testtask.model.response.BookingResponse;
import co.spribe.testtask.repository.BookingRepository;
import co.spribe.testtask.repository.UnitRepository;
import co.spribe.testtask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

            return new BookingResponse(newBooking.getId());
        } else {
            throw new UnitIsNotAvailableException(request.unitId(), request.checkInDate(), request.checkOutDate());
        }
    }

    private Booking createBooking(BookingRequest request, Unit unit, User user) {
        var newBooking = new Booking();
        newBooking.setCheckInDate(request.checkInDate());
        newBooking.setCheckOutDate(request.checkOutDate());
        // + 15% of newBooking system markup
        newBooking.setTotalCost(unit.getCost().multiply(new BigDecimal("1.15"))); // TODO: remove hard coded string from here
        newBooking.setUnit(unit);
        newBooking.setCancelled(false);
        newBooking.setUser(user);

        return newBooking;
    }

    @Transactional
    public void cancelBooking(UUID id) {
        bookingRepository
                .findById(id)
                .ifPresentOrElse(
                        booking -> {
                            booking.setCancelled(true);
                            booking
                                    .getPayments()
                                    .stream()
                                    .filter(payment -> PaymentStatus.PENDING.equals(payment.getStatus()))
                                    .forEach(payment -> payment.setStatus(PaymentStatus.CANCELED));
                        },
                        () -> { throw new BookingNotExistException(); }
                );
    }
}
