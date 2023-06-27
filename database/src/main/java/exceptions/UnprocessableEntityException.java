package exceptions;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException() {
        super();
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }
}
