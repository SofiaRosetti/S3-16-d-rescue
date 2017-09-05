package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.AbstractMessage;

/**
 * Class that represents a successful message.
 */
public class SuccessfulMessageImpl extends AbstractMessage {

    public final static String SUCCESSFUL_MESSAGE = "successful_message";

    /**
     * Creates a successful message.
     */
    public SuccessfulMessageImpl() {
        super(SUCCESSFUL_MESSAGE);
    }
}
