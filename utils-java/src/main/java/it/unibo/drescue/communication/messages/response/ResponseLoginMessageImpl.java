package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.model.EventTypeImpl;
import it.unibo.drescue.model.UserImpl;

import java.util.List;

/**
 * Class that represents a response message to the login request.
 */
public class ResponseLoginMessageImpl extends AbstractMessage implements ResponseLoginMessage {

    private final UserImpl user;
    private final List<EventTypeImpl> eventTypes;

    /**
     * Creates a message containing the user data for which a request was made.
     *
     * @param user       user data
     * @param eventTypes list of events type
     */
    public ResponseLoginMessageImpl(final UserImpl user, final List<EventTypeImpl> eventTypes) {
        super(MessageType.RESPONSE_LOGIN_MESSAGE);
        this.user = user;
        this.eventTypes = eventTypes;
    }

    @Override
    public UserImpl getUser() {
        return this.user;
    }

    @Override
    public List<EventTypeImpl> getEventsType() {
        return this.eventTypes;
    }

    @Override
    public Message build() {
        return this;
    }
}
