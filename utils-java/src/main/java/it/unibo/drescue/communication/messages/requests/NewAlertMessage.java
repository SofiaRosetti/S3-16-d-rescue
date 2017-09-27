package it.unibo.drescue.communication.messages.requests;

/**
 * Interface modelling a message for performing the submit of a new alert.
 */
public interface NewAlertMessage {

    /**
     * @return the user id
     */
    int getUserID();

    /**
     * Sets the user's id.
     *
     * @param userID user identifier
     */
    void setUserID(int userID);

    /**
     * @return the event type that the user wants to report
     */
    String getEventType();

    /**
     * Sets the event type that the user wats to report.
     *
     * @param eventType event type name
     */
    void setEventType(String eventType);

    /**
     * @return tha latitude at which the event happened
     */
    double getLatitude();

    /**
     * Sets the latitude at which the event happened.
     *
     * @param latitude latitude value
     */
    void setLatitude(double latitude);

    /**
     * @return the longitude at which the event happened
     */
    double getLongitude();

    /**
     * Sets the longitude at which the event happened.
     * @param longitude longitude value
     */
    void setLongitude(double longitude);

}
