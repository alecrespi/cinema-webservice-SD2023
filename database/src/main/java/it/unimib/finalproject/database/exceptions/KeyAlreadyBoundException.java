package it.unimib.finalproject.database.exceptions;

public class KeyAlreadyBoundException extends RuntimeException {
    public KeyAlreadyBoundException() {
        super();
    }

    public KeyAlreadyBoundException(String message) {
        super(message);
    }
}
