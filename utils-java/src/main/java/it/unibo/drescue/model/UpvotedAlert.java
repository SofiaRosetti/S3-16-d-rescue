package it.unibo.drescue.model;

/**
 * An interface modelling the upvote of an alert by a user.
 */
public interface UpvotedAlert extends ObjectModel {

    /**
     * @return the user ID
     */
    int getUserID();

    /**
     * Sets the user ID
     *
     * @param userID the user ID
     */
    void setUserID(int userID);

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
}
