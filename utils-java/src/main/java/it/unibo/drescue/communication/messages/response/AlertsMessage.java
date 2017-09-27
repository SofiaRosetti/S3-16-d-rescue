package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.model.AlertImpl;

import java.util.List;

/**
 * Interface modelling a message containing alerts.
 */
public interface AlertsMessage {

    /**
     * @return the alert list
     */
    List<AlertImpl> getAlerts();
}
