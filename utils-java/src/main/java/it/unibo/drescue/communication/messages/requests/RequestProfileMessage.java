package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.builder.MessageBuilder;

/**
 * Interface modelling a request message for obtaining user profile.
 */
public interface RequestProfileMessage extends MessageBuilder {

    /**
     * @return user's email
     */
    String getUserEmail();
}
