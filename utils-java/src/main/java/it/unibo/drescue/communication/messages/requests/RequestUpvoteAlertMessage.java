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
     * @param userID user identifier
     */
    void setUserID(int userID);

    /**
     * @return the alert identifier
     */
    int getAlertID();

    /**
     * Sets the alertID of the alert to upvote.
     *
     * @param alertID alert identifier
     */
    void setAlertID(int alertID);

    /**
     * @return the district of the alert to upvote.
     */
    String getDistrictID();

    /**
     * Sets the districtID of the alert to upvote.
     *
     * @param districtID district identifier
     */
    void setDistrictID(String districtID);
}
