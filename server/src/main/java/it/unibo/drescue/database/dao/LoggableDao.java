package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.LoggableModel;

public interface LoggableDao extends UpdatableDao {

    /**
     * Check credentials
     *
     * @param loggableModel with filled credentials fields to check
     * @return the object (with hidden pwd) if the credentials are valid, null otherwise
     */
    LoggableModel login(LoggableModel loggableModel);
}
