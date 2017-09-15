package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.EventTypeImpl;

import java.util.List;

public interface EventTypeDao extends GenericDao {

    /**
     * Get all event types
     *
     * @return a list with all events type
     * @throws DBQueryException if something goes wrong executing the findAll query
     */
    List<EventTypeImpl> findAll() throws DBQueryException;

}
