package it.unibo.drescue.model;

import java.sql.Timestamp;

/**
 * An interface modelling an event that really happened.
 */
public interface OccurredEvent extends ObjectModel {

    /**
     * @return the event timestamp
     */
    Timestamp getTimestamp();

    /**
     * Sets the occurred event timestamp
     *
     * @param timestamp the event timestamp
     */
    void setTimestamp(Timestamp timestamp);

    /**
     * @return the event latitude
     */
    double getLatitude();

    /**
     * Sets the occurred event latitude
     *
     * @param latitude the event latitude
     */
    void setLatitude(double latitude);

    /**
     * @return the event longitude
     */
    double getLongitude();

    /**
     * Sets the occurred event longitude
     *
     * @param longitude the event longitude
     */
    void setLongitude(double longitude);

    /**
     * @return the event description
     */
    String getDescription();

    /**
     * Sets the occurred event description
     *
     * @param description the event description
     */
    void setDescription(String description);

    /**
     * @return the event ID
     */
    int getEventID();

    /**
     * Sets the occurred event ID
     *
     * @param eventID the event ID
     */
    void setEventID(int eventID);

    /**
     * @return the civil protection ID
     */
    String getCpID();

    /**
     * Sets the civil protection ID
     *
     * @param cpID the civil protection ID
     */
    void setCpID(String cpID);

}
