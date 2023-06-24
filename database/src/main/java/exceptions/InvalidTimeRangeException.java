package exceptions;

public class InvalidTimeRangeException extends Exception {

    public InvalidTimeRangeException() {
        super();
    }

    public InvalidTimeRangeException(String message) {
        super(message);
    }

    public InvalidTimeRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTimeRangeException(Throwable cause) {
        super(cause);
    }
}
