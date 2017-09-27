package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.builder.MessageBuilder;
import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;

/**
 * Class that represents an error message.
 */
public class ErrorMessageImpl extends AbstractMessage implements ErrorMessage, MessageBuilder {

    private final String error;

    /**
     * Creates an error message with the given parameter.
     *
     * @param error message of error
     */
    public ErrorMessageImpl(final String error) {
        super(MessageType.ERROR_MESSAGE);
        this.error = error;
    }

    @Override
    public String getError() {
        return this.error;
    }

    @Override
    public Message build() {
        return this;
    }
}
