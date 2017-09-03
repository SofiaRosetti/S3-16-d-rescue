package it.unibo.drescue.communication.builder;

import it.unibo.drescue.communication.messages.Message;

/**
 * Interface modelling a generic message builder.
 */
public interface MessageBuilder {

    /**
     * Build the message
     *
     * @return the message builder
     */
    Message build();
}
