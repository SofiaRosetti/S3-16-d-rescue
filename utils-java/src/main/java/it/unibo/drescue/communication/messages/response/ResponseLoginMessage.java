package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.builder.MessageBuilder;
import it.unibo.drescue.model.EventType;
import it.unibo.drescue.model.User;

import java.util.List;

/**
 * Interface modelling a response message to the login request containing user data and a list of events type.
 */
public interface ResponseLoginMessage extends MessageBuilder {

    /**
     * @return user data
     */
    User getUser();

    /**
     * @return list of events type
     */
    List<EventType> getEventsType();

}
