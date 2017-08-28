package it.unibo.drescue.model;

import java.sql.Timestamp;

/**
 * An interface modelling an event that really happened.
 */
public interface OccurredEvent {

    /**
     * @return the event timestamp
     */
    Timestamp getTimestamp();

    /**
     * @return the event latitude
     */
    double getLatitude();

    /**
     * @return the event longitude
     */
    double getLongitude();

    /**
     * @return the event description
     */
    String getDescription();

    /**
     * @return the event ID
     */
    int getEventID();

    /**
     * @return the civil protection ID
     */
    String getCpID();

}
