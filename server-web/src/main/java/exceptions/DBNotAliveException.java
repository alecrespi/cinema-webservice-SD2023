package exceptions;

public class DBNotAliveException extends Exception{

    public DBNotAliveException(String message) {
        super(message);
    }

    public DBNotAliveException(String message, Throwable cause) {
        super(message, cause);
    }

}
