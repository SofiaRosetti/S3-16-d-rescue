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

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        final String newLine = System.getProperty("line.separator");

        result.append("Upvoted alert: {" + newLine);
        result.append(" User ID: " + this.getUserID() + newLine);
        result.append(" Alert ID: " + this.getAlertID() + newLine);

        result.append("}");

        return result.toString();
    }
}
