package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.MessageType;

/**
 * Class that represents a request message containing data for upvote an alert.
 */
public class RequestUpvoteAlertMessageImpl extends AbstractMessage implements RequestUpvoteAlertMessage {

    private int userID;
    private int alertID;
    private String districtID;

    public RequestUpvoteAlertMessageImpl() {
        super(MessageType.REQUEST_UPVOTE_MESSAGE);
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
    public String getDistrictID() {
        return this.districtID;
    }

    @Override
    public void setDistrictID(final String districtID) {
        this.districtID = districtID;
    }
}
