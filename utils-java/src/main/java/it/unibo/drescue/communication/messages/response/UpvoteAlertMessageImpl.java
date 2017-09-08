package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;

/**
 * Class that represents a response message for request of upvote an alert.
 */
public class UpvoteAlertMessageImpl extends AbstractMessage implements UpvoteAlertMessage {

    private final int alertID;
    private final int upvoteTotal;

    /**
     * Creates a message containing the data for which a request was made.
     *
     * @param alertID     alert identifier
     * @param upvoteTotal total number of upvote
     */
    public UpvoteAlertMessageImpl(final int alertID, final int upvoteTotal) {
        super(MessageType.UPVOTE_MESSAGE);
        this.alertID = alertID;
        this.upvoteTotal = upvoteTotal;
    }

    @Override
    public int getAlertID() {
        return this.alertID;
    }

    @Override
    public int getUpvoteTotal() {
        return this.upvoteTotal;
    }

    @Override
    public Message build() {
        return this;
    }
}
