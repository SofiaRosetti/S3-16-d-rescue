package it.unibo.drescue.communication.messages.requests;

/**
 * Interface modelling a request message for upvote an alert.
 */
public interface RequestUpvoteAlertMessage {

    /**
     * @return the user identifier
     */
    int getUserID();

    /**
     * Sets the userID of the user who wants to upvote the alert.
     *
     * @param userID
     */
    void setUserID(int userID);

    /**
     * @return the alert identifier
     */
    int getAlertID();

    /**
     * Sets the alertID of the alert to upvote.
     *
     * @param alertID
     */
    void setAlertID(int alertID);

    /**
     * @return the district of the alert to upvote.
     */
    String getDistrictID();

    /**
     * Sets the districtID of the alert to upvote.
     *
     * @param districtID
     */
    void setDistrictID(String districtID);
}
