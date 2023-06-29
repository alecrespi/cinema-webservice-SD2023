package it.unimib.finalproject.server.exceptions;

public class DbNotRespondingException extends Exception {

    public DbNotRespondingException(String message) {
        super(message);
    }

    public DbNotRespondingException(String message, Throwable cause) {
        super(message, cause);
    }
}