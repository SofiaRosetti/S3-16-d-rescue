package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBNotFoundRecordException;
import it.unibo.drescue.model.LoggableModel;

public interface LoggableDao extends UpdatableDao {

    /**
     * Check credentials
     *
     * @param loggableModel with filled credentials fields to check
     * @return the object (with hidden pwd) if the credentials are valid, null otherwise
     * @throws DBNotFoundRecordException if something gone wrong (password or id not valid)
     */
    LoggableModel login(LoggableModel loggableModel) throws DBNotFoundRecordException;
}
