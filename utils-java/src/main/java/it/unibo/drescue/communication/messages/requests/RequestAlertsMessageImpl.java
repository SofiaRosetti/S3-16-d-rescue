package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.builder.MessageBuilder;
import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.Message;

/**
 * Class that represents a message containing a request of alerts of a
 * specific area.
 */
public class RequestAlertsMessageImpl extends AbstractMessage implements RequestAlertsMessage, MessageBuilder {

    public final static String REQUEST_ALERTS_MESSAGE = "request_alerts_message";

    private final double latitude;
    private final double longitude;

    /**
     * Creates a message containing info about the area for which alerts are requested.
     *
     * @param latitude  latitude of the area for which alerts are requested
     * @param longitude longitude of the area for which alerts are requested
     */
    public RequestAlertsMessageImpl(final double latitude, final double longitude) {
        super(REQUEST_ALERTS_MESSAGE);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double getLatitude() {
        return this.latitude;
    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }

    @Override
    public Message build() {
        return this;
    }
}
