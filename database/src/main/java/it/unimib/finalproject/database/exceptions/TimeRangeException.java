package it.unimib.finalproject.database.exceptions;

public class TimeRangeException extends RuntimeException {
    public TimeRangeException() {
        super();
    }

    public TimeRangeException(String message) {
        super(message);
    }
}
