package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.builder.MessageBuilder;
import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.model.AlertImpl;

import java.util.List;

/**
 * Class that represents a message containing a list of alert.
 */
public class AlertsMessageImpl extends AbstractMessage implements AlertsMessage, MessageBuilder {

    private final List<AlertImpl> alertCollection;

    /**
     * Creates a message containing a list of alert with the given parameter.
     *
     * @param alertCollection list of alert
     */
    public AlertsMessageImpl(final List<AlertImpl> alertCollection) {
        super(MessageType.ALERTS_MESSAGE);
        this.alertCollection = alertCollection;
    }


    @Override
    public List<AlertImpl> getAlerts() {
        return this.alertCollection;
    }

    @Override
    public Message build() {
        return this;
    }
}
