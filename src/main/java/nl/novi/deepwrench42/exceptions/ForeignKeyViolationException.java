package nl.novi.deepwrench42.exceptions;

public class ForeignKeyViolationException extends RuntimeException {
    public ForeignKeyViolationException(String message) {
        super(message);
    }
}