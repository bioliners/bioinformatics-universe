package exceptions;

/**
 * Created by vadim on 7/26/17.
 */
public class IncorrectRequestException extends Exception {

    public IncorrectRequestException (String message) {
        super(message);
    }
    public IncorrectRequestException (String message, Throwable cause) {
        super(message, cause);
    }

}
