package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBDuplicatedRecordException;
import it.unibo.drescue.database.exceptions.DBNotFoundRecordException;
import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.ObjectModel;

/**
 * Interface from which all others DAO interfaces extends,
 * it shows the main methods for all objects in DB
 */
public interface GenericDao {

    /**
     * Insert a given object model into DB
     *
     * @param objectModel to insert
     * @throws DBDuplicatedRecordException if the objectModel passed is already in DB
     * @throws DBQueryException            if somethings goes wrong executing the insert query
     */
    void insert(ObjectModel objectModel) throws DBDuplicatedRecordException, DBQueryException;

    /**
     * Delete a given object model in DB
     *
     * @param objectModel to delete
     * @throws DBQueryException          if something goes wrong executing the delete query
     * @throws DBNotFoundRecordException if the objectModel passed isn't in DB
     */
    void delete(ObjectModel objectModel) throws DBNotFoundRecordException, DBQueryException;

    /**
     * Find an object with the id included in the the given objectModel
     *
     * @param objectModel the object that contains the identifier to search
     * @return the object if an object with the given id in objectModel exists, null otherwise
     */
    ObjectModel selectByIdentifier(ObjectModel objectModel);

    /**
     * Insert a given object model into DB and return the object in db
     * Utils for getting the id of an object in DB
     *
     * @param objectModel to insert
     * @return the object inserted
     * @throws DBQueryException            if something goes wrong executing the insert query
     * @throws DBDuplicatedRecordException if the objectModel passed is already in DB
     * @throws DBNotFoundRecordException   something goes wrong in getting record after insert
     */
    ObjectModel insertAndGet(ObjectModel objectModel)
            throws DBQueryException, DBDuplicatedRecordException, DBNotFoundRecordException;
}
