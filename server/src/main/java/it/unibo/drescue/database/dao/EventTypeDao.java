package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.EventType;

import java.util.List;

public interface EventTypeDao extends GenericDao {

    /**
     * Get all event types
     *
     * @return a list with all events type
     */
    List<EventType> findAll();

}
