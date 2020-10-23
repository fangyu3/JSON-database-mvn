package server.exceptions;

public class InvalidIndexException extends RuntimeException{
    public InvalidIndexException(String message) {
        super(message);
    }
}
