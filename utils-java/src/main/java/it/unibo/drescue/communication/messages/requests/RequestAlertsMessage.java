package it.unibo.drescue.communication.messages.requests;

/**
 * Interface modelling a message for performing an alert request.
 */
public interface RequestAlertsMessage {

    /**
     * @return latitude of the area for which alerts are requested
     */
    double getLatitude();

    /**
     * @return longitude of the area for which alerts are requested
     */
    double getLongitude();

}
