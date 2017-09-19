package it.unibo.drescue.database;

import it.unibo.drescue.database.dao.GenericDaoAbstract;
import it.unibo.drescue.database.exceptions.DBConnectionException;

/**
 * Interface that allows communication with database.
 */
public interface DBConnection {


    /**
     * Establish the connection with DB
     *
     * @throws DBConnectionException if something gone wrong establishing the connection
     */
    void openConnection() throws DBConnectionException;

    /**
     * Close the current open connection
     *
     * @throws DBConnectionException if something gone wrong closing the connection
     */
    void closeConnection() throws DBConnectionException;

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
     * @throws DBConnectionException if something gone wrong
     */
    GenericDaoAbstract getDAO(Table table) throws DBConnectionException;

    enum Table {
        USER,
        DISTRICT,
        EVENT_TYPE,
        ALERT,
        CIVIL_PROTECTION,
        CP_AREA,
        UPVOTED_ALERT,
        RESCUE_TEAM,
        CP_ENROLLMENT
    }

}
