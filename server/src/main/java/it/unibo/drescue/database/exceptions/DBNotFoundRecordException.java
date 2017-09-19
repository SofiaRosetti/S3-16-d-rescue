package it.unibo.drescue.database.exceptions;

/**
 * Exception called when an error occurs while searching a record not found in database.
 */
public class DBNotFoundRecordException extends Exception {
    private static final String NOT_FOUND_EXCEPTION =
            "Exception for searching a record not found in DB";

    private final String message;

    public DBNotFoundRecordException() {
        this.message = NOT_FOUND_EXCEPTION;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
