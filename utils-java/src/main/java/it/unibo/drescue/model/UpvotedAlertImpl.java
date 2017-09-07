package it.unibo.drescue.model;

/**
 * Upvoted alert implementation
 */
public class UpvotedAlertImpl implements UpvotedAlert {

    private int userID;
    private int alertID;

    public UpvotedAlertImpl(final int userID, final int alertID) {
        this.userID = userID;
        this.alertID = alertID;
    }

    @Override
    public int getUserID() {
        return this.userID;
    }

    @Override
    public void setUserID(final int userID) {
        this.userID = userID;
    }

    @Override
    public int getAlertID() {
        return this.alertID;
    }

    @Override
    public void setAlertID(final int alertID) {
        this.alertID = alertID;
    }
}
