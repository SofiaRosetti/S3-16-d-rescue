package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;

/**
 * Class that represents a message containing a request of user data.
 */
public class RequestProfileMessageImpl extends AbstractMessage implements RequestProfileMessage {

    private final String userEmail;

    /**
     * Creates a message containing the user's email for which data are requested.
     *
     * @param userEmail user's email
     */
    public RequestProfileMessageImpl(final String userEmail) {
        super(MessageType.REQUEST_PROFILE_MESSAGE);
        this.userEmail = userEmail;
    }

    @Override
    public String getUserEmail() {
        return this.userEmail;
    }

    @Override
    public Message build() {
        return this;
    }
}
