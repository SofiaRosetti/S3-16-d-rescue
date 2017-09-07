package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;

/**
 * Class that rappresents a response message to the login request.
 */
public class ResponseLoginMessageImpl extends AbstractMessage implements ResponseLoginMessage {

    private final int userID;

    /**
     * Creates a message containing the user identifier for which a request was made.
     *
     * @param userID user identifier
     */
    public ResponseLoginMessageImpl(final int userID) {
        super(MessageType.RESPONSE_LOGIN_MESSAGE);
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
