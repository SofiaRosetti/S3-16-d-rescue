package it.unibo.drescue.database.dao;

public interface LoggableDao extends UpdatableDao {

    /**
     * Check credentials
     *
     * @param identifier
     * @param password
     * @return true if the credentials are valid, false otherwise
     */
    boolean login(String identifier, String password);
}
