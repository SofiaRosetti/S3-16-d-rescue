package it.unibo.drescue.model;

/**
 * An interface modelling an Alert.
 */

public interface EventType {

    /**
     * @return the event ID
     */
    int getEventID();

    /**
     * @return the event name
     */
    String getEventName();

}
