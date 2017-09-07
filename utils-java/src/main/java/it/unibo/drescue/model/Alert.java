package it.unibo.drescue.model;

import java.sql.Timestamp;

/**
 * An interface modelling an Alert.
 */

public interface Alert extends ObjectModel {

    /**
     * @return the alert ID
     */
    int getAlertID();

    /**
     * @return the alert timestamp
     */
    Timestamp getTimestamp();

    /**
     * @return the alert latitude
     */
    double getLatitude();

    /**
     * @return the alert longitude
     */
    double getLongitude();

    /**
     * @return the ID of the user who sent the alert
     */
    int getUserID();

    /**
     * @return the alert event ID
     */
    int getEventID();

    /**
     * @return the alert district ID
     */
    String getDistrictID();

    /**
     * @return the number of upvotes of the alert
     */
    int getUpvotes();
}
