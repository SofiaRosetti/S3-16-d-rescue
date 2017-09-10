package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.model.EventType;
import it.unibo.drescue.model.User;

import java.util.List;

/**
 * Class that represents a response message to the login request.
 */
public class ResponseLoginMessageImpl extends AbstractMessage implements ResponseLoginMessage {

    private final User user;
    private final List<EventType> eventTypes;

    /**
     * Creates a message containing the user data for which a request was made.
     *
     * @param user       user data
     * @param eventTypes list of events type
     */
    public ResponseLoginMessageImpl(final User user, final List<EventType> eventTypes) {
        super(MessageType.RESPONSE_LOGIN_MESSAGE);
        this.user = user;
        this.eventTypes = eventTypes;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public List<EventType> getEventsType() {
        return this.eventTypes;
    }

    @Override
    public Message build() {
        return this;
    }
}
