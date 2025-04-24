package co.spribe.testtask.exception;

public class IncorrectDateRangeException extends RuntimeException {
    public IncorrectDateRangeException() {
        super("checkInDate should not be greater than checkOutDate");
    }
}
