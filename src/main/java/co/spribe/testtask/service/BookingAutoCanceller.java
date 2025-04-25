package co.spribe.testtask.service;

import co.spribe.testtask.model.entity.Booking;
import co.spribe.testtask.model.entity.BookingStatus;
import co.spribe.testtask.model.entity.EventType;
import co.spribe.testtask.model.entity.PaymentStatus;
import co.spribe.testtask.repository.BookingRepository;
import co.spribe.testtask.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingAutoCanceller {
    private final TaskScheduler taskScheduler;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final EventService eventService;

    @Transactional
    public void schedulePaymentCheck(Booking booking) {
        taskScheduler.schedule(() -> {
            paymentRepository
                    .findFirstByBookingIdAndStatusOrderByCreatedAtDesc(booking.getId(), PaymentStatus.PENDING)
                    .ifPresent(payment -> {
                        booking.setStatus(BookingStatus.EXPIRED);
                        payment.setStatus(PaymentStatus.EXPIRED);
                        bookingRepository.save(booking);
                        paymentRepository.save(payment);
                        eventService.storeEvent(EventType.BOOKING_EXPIRED, booking, booking.getUser());
                        log.info("Didn't receive payment for {} booking, so it has been canceled", booking.getId());
                    });
        }, Instant.now().plus(Duration.ofMinutes(15)));
    }
}
