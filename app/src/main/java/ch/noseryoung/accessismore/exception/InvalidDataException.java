package ch.noseryoung.accessismore.exception;

public class InvalidDataException extends Exception {
    public InvalidDataException (String message) {
        System.err.println(message);
    }
}
