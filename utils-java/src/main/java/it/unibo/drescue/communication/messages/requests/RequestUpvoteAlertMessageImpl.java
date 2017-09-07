package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;

/**
 * Class that represents a request message containing data for upvote an alert.
 */
public class RequestUpvoteAlertMessageImpl extends AbstractMessage implements RequestUpvoteAlertMessage {

    private final int userID;
    private final int alertID;

    /**
     * Creates a message containing the user and alert identifier for which upvote is requested.
     *
     * @param userID  user identifier
     * @param alertID alert identifier
     */
    public RequestUpvoteAlertMessageImpl(final int userID, final int alertID) {
        super(MessageType.REQUEST_UPVOTE_MESSAGE);
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

    @Override
    public Message build() {
        return this;
    }
}
