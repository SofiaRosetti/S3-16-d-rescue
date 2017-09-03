package it.unibo.drescue.communication.messages;

/**
 * Class that models a simple message.
 */
public abstract class AbstractMessage implements Message {

    private final String messageType;

    /**
     * Creates a simple message of the given type
     *
     * @param messageType type of the message
     */
    public AbstractMessage(final String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String getMessageType() {
        return this.messageType;
    }
}
