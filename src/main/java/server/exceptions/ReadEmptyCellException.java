package server.exceptions;

public class ReadEmptyCellException extends RuntimeException{
    public ReadEmptyCellException(String message) {
        super(message);
    }
}
