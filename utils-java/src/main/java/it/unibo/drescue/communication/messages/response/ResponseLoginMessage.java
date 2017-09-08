package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.builder.MessageBuilder;
import it.unibo.drescue.model.User;

/**
 * Interface modelling a response message to the login request containing user data.
 */
public interface ResponseLoginMessage extends MessageBuilder {

    /**
     * @return user data
     */
    User getUser();
}
