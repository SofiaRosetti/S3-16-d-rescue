package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.builder.MessageBuilder;
import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;

/**
 * Class that represents a successful message.
 */
public class SuccessfulMessageImpl extends AbstractMessage implements MessageBuilder {

    /**
     * Creates a successful message.
     */
    public SuccessfulMessageImpl() {
        super(MessageType.SUCCESSFUL_MESSAGE);
    }

    @Override
    public Message build() {
        return this;
    }
}
