package com.veinhorn.booking.service.service;

import com.veinhorn.booking.service.exception.PaymentCannotBeProcessed;
import com.veinhorn.booking.service.exception.ResourceNotFoundException;
import com.veinhorn.booking.service.model.entity.*;
import com.veinhorn.booking.service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final EventService eventService;

    @Transactional
    public void processPayment(Booking booking) {
        var payment = new Payment();
        payment.setStatus(PaymentStatus.PENDING);
        payment.setBooking(booking);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setAmount(payment.getBooking().getUnit().getCost().multiply(new BigDecimal("1.15")));

        paymentRepository.save(payment);
    }

    @Transactional
    public void acceptPayment(UUID paymentId) {
        paymentRepository
                .findById(paymentId)
                .ifPresentOrElse(payment -> {
                    if (PaymentStatus.PENDING.equals(payment.getStatus())) {
                        payment.getBooking().setStatus(BookingStatus.CONFIRMED);
                        payment.setStatus(PaymentStatus.PAID);
                        eventService.storeEvent(EventType.BOOKING_CONFIRMED, payment.getBooking(), payment.getBooking().getUser());
                        log.info("Payment {} has been successfully accepted for {} booking", payment.getId(), payment.getBooking().getId());
                    } else {
                        throw new PaymentCannotBeProcessed(payment.getId(), payment.getStatus());
                    }
                }, () -> { throw new ResourceNotFoundException("Payment %s not found".formatted(paymentId)); });
    }
}
