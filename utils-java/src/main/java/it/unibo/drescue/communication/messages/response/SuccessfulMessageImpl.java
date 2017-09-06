package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.builder.MessageBuilder;
import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;

/**
 * Class that represents a successful message.
 */
public class SuccessfulMessageImpl extends AbstractMessage implements MessageBuilder {

    public final static String SUCCESSFUL_MESSAGE = "successful_message";

    /**
     * Creates a successful message.
     */
    public SuccessfulMessageImpl() {
        super(SUCCESSFUL_MESSAGE);
    }

    @Override
    public Message build() {
        return this;
    }
}
