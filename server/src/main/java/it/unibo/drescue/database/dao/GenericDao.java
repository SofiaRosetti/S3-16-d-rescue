package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.ObjectModel;

public interface GenericDao {

    /**
     * Insert a given object model into DB
     *
     * @param objectModel to insert
     *                    TODO Exception if the given object already exists in the DB
     */
    void insert(ObjectModel objectModel);

    /**
     * Delete a given object model in DB
     *
     * @param objectModel to delete
     *                    TODO Exception if the object NOT already exists in the DB
     */
    void delete(ObjectModel objectModel);

    /**
     * Find an object with the id included in the the given objectModel
     *
     * @param objectModel the object that contains the identifier to search
     * @return the object if an object with the given id in objectModel exists
     */
    ObjectModel selectByIdentifier(ObjectModel objectModel);
}
