package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.ObjectModel;

/**
 * Interface from which all DAO that need an update method extends,
 * it extends the GenericDao interface
 */
public interface UpdatableDao extends GenericDao {

    /**
     * Update a specific object with the new data passed in the given object
     *
     * @param objectModel specify the object model to update filled with the new value/values to change
     * @throws DBQueryException if something goes wrong executing the update query
     */
    void update(ObjectModel objectModel) throws DBQueryException;
}
