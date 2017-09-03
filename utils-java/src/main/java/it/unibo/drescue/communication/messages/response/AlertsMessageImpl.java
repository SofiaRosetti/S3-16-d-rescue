package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.model.AlertImpl;

import java.util.List;

/**
 * Class that represents a message containing a list of alert.
 */
public class AlertsMessageImpl extends AbstractMessage implements AlertsMessage {

    public final static String ALERTS_MESSAGE = "alerts_message";

    private final List<AlertImpl> alertCollection;

    /**
     * Creates a message containing a list of alert with the given parameter.
     *
     * @param alertCollection list of alert
     */
    public AlertsMessageImpl(final List<AlertImpl> alertCollection) {
        super(ALERTS_MESSAGE);
        this.alertCollection = alertCollection;
    }


    @Override
    public List<AlertImpl> getAlerts() {
        return this.alertCollection;
    }
}
