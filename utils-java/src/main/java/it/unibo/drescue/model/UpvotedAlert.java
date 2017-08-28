package it.unibo.drescue.model;

/**
 * An interface modelling the upvote of an alert by a user.
 */
public interface UpvotedAlert {

    /**
     * @return the user ID
     */
    int getUserID();

    /**
     * @return the alert ID
     */
    int getAlertID();
}
