package exceptions;

public class InvalidSeatLocatorException extends Exception {

    public InvalidSeatLocatorException() {
        super();
    }

    public InvalidSeatLocatorException(String message) {
        super(message);
    }

    public InvalidSeatLocatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSeatLocatorException(Throwable cause) {
        super(cause);
    }
}
