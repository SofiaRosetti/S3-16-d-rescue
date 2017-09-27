package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.MessageType;

/**
 * Class that represent a message for performing the submit of a new alert.
 */
public class NewAlertMessageImpl extends AbstractMessage implements NewAlertMessage {

    private int userID;
    private String eventType;
    private double latitude;
    private double longitude;

    public NewAlertMessageImpl() {
        super(MessageType.NEW_ALERT_MESSAGE);
    }

    @Override
    public int getUserID() {
        return this.userID;
    }

    @Override
    public void setUserID(final int userID) {
        this.userID = userID;
    }

    @Override
    public String getEventType() {
        return this.eventType;
    }

    @Override
    public void setEventType(final String eventType) {
        this.eventType = eventType;
    }

    @Override
    public double getLatitude() {
        return this.latitude;
    }

    @Override
    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }

    @Override
    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }
}
