package it.unibo.drescue.communication.builder.requests;

import it.unibo.drescue.communication.builder.MessageBuilder;

/**
 * Builder interface for new alert messages.
 */
public interface NewAlertMessageBuilder extends MessageBuilder {

    /**
     * @param userID
     * @return the builder with the given userID
     */
    NewAlertMessageBuilder setUserID(int userID);

    /**
     * @param eventType
     * @return the builder with the given eventType
     */
    NewAlertMessageBuilder setEventType(String eventType);

    /**
     * @param latitude
     * @return the builder with the given latitude
     */
    NewAlertMessageBuilder setLatitude(double latitude);

    /**
     * @param longitude
     * @return the builder with the given longitude
     */
    NewAlertMessageBuilder setLongitude(double longitude);

}
