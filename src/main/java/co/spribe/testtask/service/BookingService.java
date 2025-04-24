package co.spribe.testtask.service;

import co.spribe.testtask.exception.UnitIsNotAvailable;
import co.spribe.testtask.model.entity.Booking;
import co.spribe.testtask.model.request.BookingRequest;
import co.spribe.testtask.repository.BookingRepository;
import co.spribe.testtask.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final UnitService unitService;
    private final UnitRepository unitRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public void createBooking(BookingRequest request) {
        // check here that unit exists
        var unit = unitRepository
                .findById(request.unitId())
                .orElseThrow(() -> new RuntimeException("Unit does not exist"));

        // check that unit is available for booking
        var isUnitAvailable = unitService.isUnitAvailable(unit, request.checkInDate(), request.checkOutDate());

        if (isUnitAvailable) {
            var booking = new Booking();
            booking.setCheckInDate(request.checkInDate());
            booking.setCheckOutDate(request.checkOutDate());
            booking.setTotalCost(unit.getCost().multiply(new BigDecimal("1.15")));
            booking.setUnit(unit);

            bookingRepository.save(booking);
        } else {
            throw new UnitIsNotAvailable(request.unitId());
        }
    }
}
