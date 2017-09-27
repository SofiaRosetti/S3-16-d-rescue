package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.builder.MessageBuilder;
import it.unibo.drescue.model.EventTypeImpl;
import it.unibo.drescue.model.UserImpl;

import java.util.List;

/**
 * Interface modelling a response message to the login request containing user data and a list of events type.
 */
public interface ResponseLoginMessage extends MessageBuilder {

    /**
     * @return user data
     */
    UserImpl getUser();

    /**
     * @return list of events type
     */
    List<EventTypeImpl> getEventsType();

}
