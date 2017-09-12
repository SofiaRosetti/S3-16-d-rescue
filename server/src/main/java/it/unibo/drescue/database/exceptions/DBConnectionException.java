package it.unibo.drescue.database.exceptions;

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
