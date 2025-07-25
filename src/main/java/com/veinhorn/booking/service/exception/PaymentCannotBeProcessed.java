package com.veinhorn.booking.service.exception;

import com.veinhorn.booking.service.model.entity.PaymentStatus;

import java.util.UUID;

public class PaymentCannotBeProcessed extends RuntimeException {
    public PaymentCannotBeProcessed(UUID paymentId, PaymentStatus status) {
        super("Payment %s is in %s status and cannot be accepted.".formatted(paymentId, status));
    }
}
