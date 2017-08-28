package it.unibo.drescue.model;

/**
 * Upvoted alert implementation
 */
public class UpvotedAlertImpl implements UpvotedAlert {

    private final int userID;
    private final int alertID;

    public UpvotedAlertImpl(final int userID, final int alertID) {
        this.userID = userID;
        this.alertID = alertID;
    }

    @Override
    public int getUserID() {
        return this.userID;
    }

    @Override
    public int getAlertID() {
        return this.alertID;
    }
}
