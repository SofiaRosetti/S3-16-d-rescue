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
     * Sets the alert ID
     *
     * @param alertID the alert ID
     */
    void setAlertID(int alertID);

    /**
     * @return the alert timestamp
     */
    Timestamp getTimestamp();

    /**
     * Sets the alert timestamp
     *
     * @param timestamp the alert timestamp
     */
    void setTimestamp(Timestamp timestamp);

    /**
     * @return the alert latitude
     */
    double getLatitude();

    /**
     * Sets the alert latitude
     *
     * @param latitude the alert latitude
     */
    void setLatitude(double latitude);

    /**
     * @return the alert longitude
     */
    double getLongitude();

    /**
     * Sets the alert longitude
     *
     * @param longitude the alert longitude
     */
    void setLongitude(double longitude);

    /**
     * @return the ID of the user who sent the alert
     */
    int getUserID();

    /**
     * Sets the user ID
     *
     * @param userID the user ID
     */
    void setUserID(int userID);

    /**
     * @return the alert event name
     */
    String getEventName();

    /**
     * Sets the event name
     *
     * @param eventName the event name
     */
    void setEventName(String eventName);

    /**
     * @return the alert district ID
     */
    String getDistrictID();

    /**
     * Sets the district ID
     *
     * @param districtID the district ID
     */
    void setDistrictID(String districtID);

    /**
     * @return the number of upvotes of the alert
     */
    int getUpvotes();

    /**
     * Sets the alert upvotes
     *
     * @param upvotes the alert upvotes
     */
    void setUpvotes(int upvotes);

    /**
     * Creates a string representation of the alert
     * useful to be included in civil protection views
     *
     * @return a string representing the alert
     */
    String toPrintableString();
}
