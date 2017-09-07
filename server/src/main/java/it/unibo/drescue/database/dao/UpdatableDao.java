package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.ObjectModel;

public interface UpdatableDao extends GenericDao {

    /**
     * Update a specific object with the new data passed in the given object
     *
     * @param objectModel specify the object model to update filled with the new value/values to change
     *                    TODO handle exception
     */
    void update(ObjectModel objectModel);
}
