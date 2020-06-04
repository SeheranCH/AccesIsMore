package ch.noseryoung.accessismore.exception;

public class UserAlreadyExistingException extends Exception {
    public UserAlreadyExistingException (String message) {
        System.err.println(message);
    }
}
