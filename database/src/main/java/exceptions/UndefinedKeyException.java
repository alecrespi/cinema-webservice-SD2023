package exceptions;

public class UndefinedKeyException extends RuntimeException {
    public UndefinedKeyException() {
        super();
    }

    public UndefinedKeyException(String message) {
        super(message);
    }
}
