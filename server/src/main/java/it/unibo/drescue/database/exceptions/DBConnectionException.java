package it.unibo.drescue.database.exceptions;

/**
 * Exception called when an error occurs while connecting to the database.
 */
public class DBConnectionException extends Exception {
    private final String message;

    public DBConnectionException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
