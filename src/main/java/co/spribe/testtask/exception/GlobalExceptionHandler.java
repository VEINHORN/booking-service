package co.spribe.testtask.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    public record ErrorResponse(String message) {}
}
