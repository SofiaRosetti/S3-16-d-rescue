package it.unibo.drescue.communication.builder.requests;

import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.requests.NewAlertMessageImpl;

/**
 * Builder class for new alert messages.
 */
public class NewAlertMessageBuilderImpl implements NewAlertMessageBuilder {

    private final NewAlertMessageImpl message;

    public NewAlertMessageBuilderImpl() {
        this.message = new NewAlertMessageImpl();
    }

    @Override
    public NewAlertMessageBuilder setUserID(final int userID) {
        this.message.setUserID(userID);
        return this;
    }

    @Override
    public NewAlertMessageBuilder setEventType(final String eventType) {
        this.message.setEventType(eventType);
        return this;
    }

    @Override
    public NewAlertMessageBuilder setLatitude(final double latitude) {
        this.message.setLatitude(latitude);
        return this;
    }

    @Override
    public NewAlertMessageBuilder setLongitude(final double longitude) {
        this.message.setLongitude(longitude);
        return this;
    }

    @Override
    public Message build() {
        return this.message;
    }
}
