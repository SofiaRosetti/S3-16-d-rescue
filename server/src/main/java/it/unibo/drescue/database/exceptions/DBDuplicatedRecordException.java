package it.unibo.drescue.database.exceptions;

/**
 * Exception called when an error occurs while trying to insert a duplicated record.
 */
public class DBDuplicatedRecordException extends Exception {

    private static final String DUPLICATED_MESSAGE =
            "Exception for trying to insert a duplicated record";

    private final String message;

    public DBDuplicatedRecordException() {
        this.message = DUPLICATED_MESSAGE;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
