package common;

/**
 * Created by rarques on 6/8/2017.
 */
public class DependencyException extends Exception {

    public DependencyException(Exception cause) {
        super(cause);
    }

    public DependencyException(String message) {
        super(message);
    }
}
