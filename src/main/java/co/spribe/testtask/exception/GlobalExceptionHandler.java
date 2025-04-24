package co.spribe.testtask.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnitDoesNotExist.class)
    public ResponseEntity<ErrorResponse> handleUnitDoesNotExistException(UnitDoesNotExist e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(UnitIsNotAvailable.class)
    public ResponseEntity<ErrorResponse> handleUnitIsNotAvailableException(UnitIsNotAvailable e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    public record ErrorResponse(String message) {}
}
