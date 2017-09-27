package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.EventType;

import java.util.List;

/**
 * Interface of DAO for EventType objects, it extends GenericDao
 * and specify some other useful methods for this objectModel
 */
public interface EventTypeDao extends GenericDao {

    /**
     * Get all event types
     *
     * @return a list with all events type
     * @throws DBQueryException if something goes wrong executing the findAll query
     */
    List<EventType> findAll() throws DBQueryException;

}
