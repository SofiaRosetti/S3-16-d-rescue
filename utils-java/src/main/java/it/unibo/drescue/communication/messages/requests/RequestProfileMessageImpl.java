package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.builder.MessageBuilder;
import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;

/**
 * Class that represents a message containing a request of user data.
 */
public class RequestProfileMessageImpl extends AbstractMessage implements RequestProfileMessage, MessageBuilder {

    private final int userID;

    /**
     * Creates a message containing the user's identifier for which data are requested.
     *
     * @param userID user's identifier
     */
    public RequestProfileMessageImpl(final int userID) {
        super(MessageType.REQUEST_PROFILE_MESSAGE);
        this.userID = userID;
    }

    @Override
    public int getUserID() {
        return this.userID;
    }

    @Override
    public Message build() {
        return this;
    }
}
