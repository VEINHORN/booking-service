package co.spribe.testtask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnitNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUnitDoesNotExistException(UnitNotExistException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(UnitIsNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleUnitIsNotAvailableException(UnitIsNotAvailableException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(IncorrectDateRangeException.class)
    public ResponseEntity<ErrorResponse> handleIncorrectDateRangeException(IncorrectDateRangeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(PaymentCannotBeProcessed.class)
    public ResponseEntity<ErrorResponse> handlePaymentCannotBeProcessedException(PaymentCannotBeProcessed e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookingNotExistException.class)
    public void handleBookingNotExistException() {}

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    public record ErrorResponse(String message) {}
}
