package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.builder.MessageBuilder;

/**
 * Interface modelling a response message containing the total number of upvote of an alert.
 */
public interface UpvoteAlertMessage extends MessageBuilder {

    /**
     * @return the alert identifier
     */
    int getAlertID();

    /**
     * @return the total number of upvote
     */
    int getUpvoteTotal();

}
