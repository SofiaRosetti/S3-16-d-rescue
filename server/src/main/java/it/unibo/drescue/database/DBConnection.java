package it.unibo.drescue.database;

import it.unibo.drescue.database.dao.GenericDaoAbstract;

import java.sql.SQLException;

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

    /**
     * Factory method for creating DAO of the specified table
     *
     * @param table specify the db table to access
     * @return the DAO for the specified table
     * @throws SQLException if connection is closed or null
     */
    GenericDaoAbstract getDAO(Table table) throws SQLException;

    enum Table {
        USER,
        DISTRICT,
        EVENT_TYPE,
        ALERT
    }

}
