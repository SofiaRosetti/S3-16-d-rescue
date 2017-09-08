package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.model.User;

/**
 * Class that represents a response message to the login request.
 */
public class ResponseLoginMessageImpl extends AbstractMessage implements ResponseLoginMessage {

    private final User user;

    /**
     * Creates a message containing the user data for which a request was made.
     *
     * @param user user data
     */
    public ResponseLoginMessageImpl(final User user) {
        super(MessageType.RESPONSE_LOGIN_MESSAGE);
        this.user = user;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public Message build() {
        return this;
    }
}
