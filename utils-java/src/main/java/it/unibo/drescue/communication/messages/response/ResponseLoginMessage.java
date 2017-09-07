package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.builder.MessageBuilder;

/**
 * Interface modelling a response message to the login request containing user identifier.
 */
public interface ResponseLoginMessage extends MessageBuilder {

    /**
     * @return user identifier
     */
    int getUserID();
}
