package it.unibo.drescue.communication.messages;

/**
 * Class that models a simple routing message.
 */
public abstract class AbstractRoutingMessage extends AbstractMessage implements RoutingMessage {

    private String from;
    private String to;

    /**
     * Creates a simple routing message of the given type.
     *
     * @param messageType type of the message
     */
    public AbstractRoutingMessage(final String messageType) {
        super(messageType);
    }

    @Override
    public String getFrom() {
        return this.from;
    }

    @Override
    public void setFrom(final String from) {
        this.from = from;
    }

    @Override
    public String getTo() {
        return this.to;
    }

    @Override
    public void setTo(final String to) {
        this.to = to;
    }
}
