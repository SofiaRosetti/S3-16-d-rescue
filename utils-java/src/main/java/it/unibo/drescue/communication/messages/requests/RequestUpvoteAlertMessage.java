package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.builder.MessageBuilder;

/**
 * Interface modelling a request message for upvote an alert.
 */
public interface RequestUpvoteAlertMessage extends MessageBuilder {

    /**
     * @return the user identifier
     */
    int getUserID();

    /**
     * @return the alert identifier
     */
    int getAlertID();
}
