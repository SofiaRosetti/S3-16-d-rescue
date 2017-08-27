package it.unibo.drescue.database;

/**
 * A class that allows communication with database
 */
public interface DBConnection {


    /**
     * Establish the connection with DB
     */
    void openConnection();

    /**
     * Close the current open connection
     */
    void closeConnection();

    /**
     * Check if connection is still valid
     *
     * @return true if the connection responds within 5 seconds, false otherwise
     */
    boolean isValid();

}
