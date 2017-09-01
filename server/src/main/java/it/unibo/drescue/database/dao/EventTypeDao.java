package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.EventType;

import java.util.List;

public interface EventTypeDao {


    /**
     * Find a specific event type with the given id
     *
     * @param eventTypeId specify the eventType to find
     * @return the EventType if a event type with the given id exists
     */
    EventType findById(int eventTypeId);

    /**
     * Get all event types
     *
     * @return a list with all events type
     */
    List<EventType> findAll();

    /**
     * Insert a given event type into DB
     *
     * @param eventType to insert
     * @return true if the given event type is added into DB
     * false if the given event type already exists in the DB
     */
    boolean insert(EventType eventType);

    /**
     * Delete an event type from DB
     *
     * @param eventType to delete
     * @return false if something goes wrong
     */
    boolean delete(EventType eventType);

}
