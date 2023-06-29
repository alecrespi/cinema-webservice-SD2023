package it.unimib.finalproject.database.exceptions;

public class UnreleasableKeysException extends RuntimeException {
    public UnreleasableKeysException() {
        super();
    }

    public UnreleasableKeysException(String message) {
        super(message);
    }
}
