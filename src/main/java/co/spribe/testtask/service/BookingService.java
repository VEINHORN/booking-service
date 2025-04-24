package co.spribe.testtask.service;

import co.spribe.testtask.exception.*;
import co.spribe.testtask.model.entity.Booking;
import co.spribe.testtask.model.entity.BookingStatus;
import co.spribe.testtask.model.entity.Unit;
import co.spribe.testtask.model.request.BookingRequest;
import co.spribe.testtask.model.response.BookingResponse;
import co.spribe.testtask.repository.BookingRepository;
import co.spribe.testtask.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {
    private final UnitService unitService;
    private final UnitRepository unitRepository;
    private final BookingRepository bookingRepository;
    private final TaskScheduler taskScheduler;

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        if (request.checkOutDate().isBefore(request.checkInDate())) {
            throw new IncorrectDateRangeException();
        }

        var unit = unitRepository
                .findById(request.unitId())
                .orElseThrow(() -> new UnitNotExistException(request.unitId()));

        var isUnitAvailable = unitService.isUnitAvailable(unit, request.checkInDate(), request.checkOutDate());

        if (isUnitAvailable) {
            var savedBooking = bookingRepository.save(createBooking(request, unit));
            schedulePaymentCheck(savedBooking);

            return new BookingResponse(savedBooking.getId());
        } else {
            throw new UnitIsNotAvailableException(request.unitId(), request.checkInDate(), request.checkOutDate());
        }
    }

    private void schedulePaymentCheck(Booking savedBooking) {
        taskScheduler.schedule(() -> {
            bookingRepository
                    .findById(savedBooking.getId())
                    .ifPresent(booking -> {
                        if (BookingStatus.WAITING_FOR_PAYMENT.equals(booking.getStatus())) {
                            booking.setStatus(BookingStatus.CANCELED);
                            bookingRepository.save(booking);
                            log.info("Didn't receive payment for {} booking, so it has been canceled.", booking.getId());
                        }
                    });
        }, Instant.now().plus(Duration.ofMinutes(15)));
    }

    private Booking createBooking(BookingRequest request, Unit unit) {
        var newBooking = new Booking();
        newBooking.setCheckInDate(request.checkInDate());
        newBooking.setCheckOutDate(request.checkOutDate());
        // + 15% of newBooking system markup
        newBooking.setTotalCost(unit.getCost().multiply(new BigDecimal("1.15"))); // TODO: remove hard coded string from here
        newBooking.setUnit(unit);
        newBooking.setStatus(BookingStatus.WAITING_FOR_PAYMENT);

        return newBooking;
    }

    @Transactional
    public void cancelBooking(UUID id) {
        bookingRepository
                .findById(id)
                .ifPresentOrElse(
                        booking -> {
                            if (BookingStatus.CANCELED.equals(booking.getStatus())) {
                                throw new BookingCancellationException("Cannot cancel exception which is in %s status".formatted(booking.getStatus()));
                            }

                            booking.setStatus(BookingStatus.CANCELED);
                        },
                        () -> { throw new BookingNotExistException(); }
                );
    }
}
