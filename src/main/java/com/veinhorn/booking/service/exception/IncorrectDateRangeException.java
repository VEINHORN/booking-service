package com.veinhorn.booking.service.exception;

public class IncorrectDateRangeException extends RuntimeException {
    public IncorrectDateRangeException() {
        super("checkInDate should not be greater than checkOutDate");
    }
}
