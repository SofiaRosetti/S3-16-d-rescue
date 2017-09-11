package it.unibo.drescue.database.exceptions;

public class DBQueryException extends Exception {
    private final String message;

    public DBQueryException(final String message) {
        this.message = message;
    }
}
