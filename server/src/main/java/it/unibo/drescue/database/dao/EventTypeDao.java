package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.EventType;

import java.util.List;

public interface EventTypeDao extends GenericDao {

    /**
     * Find a specific event type with the given name
     *
     * @param eventName specify the eventType to find
     * @return the EventType if a event type with the given id exists
     */
    EventType findByName(String eventName);

    /**
     * Get all event types
     *
     * @return a list with all events type
     */
    List<EventType> findAll();

}
