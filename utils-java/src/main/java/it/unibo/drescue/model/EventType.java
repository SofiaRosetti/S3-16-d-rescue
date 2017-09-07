package it.unibo.drescue.model;

/**
 * An interface modelling an Alert.
 */

public interface EventType extends ObjectModel {

    /**
     * @return the event ID
     */
    int getEventID();

    /**
     * Sets the event ID
     *
     * @param eventID the event ID
     */
    void setEventID(int eventID);

    /**
     * @return the event name
     */
    String getEventName();

    /**
     * Sets the event name
     *
     * @param eventName the event name
     */
    void setEventName(String eventName);

}
