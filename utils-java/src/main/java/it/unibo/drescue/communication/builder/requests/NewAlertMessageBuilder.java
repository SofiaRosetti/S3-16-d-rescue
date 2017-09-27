package it.unibo.drescue.communication.builder.requests;

import it.unibo.drescue.communication.builder.MessageBuilder;

/**
 * Builder interface for new alert messages.
 */
public interface NewAlertMessageBuilder extends MessageBuilder {

    /**
     * @param userID user identifier
     * @return the builder with the given userID
     */
    NewAlertMessageBuilder setUserID(int userID);

    /**
     * @param eventType event type name
     * @return the builder with the given eventType
     */
    NewAlertMessageBuilder setEventType(String eventType);

    /**
     * @param latitude latitude value
     * @return the builder with the given latitude
     */
    NewAlertMessageBuilder setLatitude(double latitude);

    /**
     * @param longitude longitude value
     * @return the builder with the given longitude
     */
    NewAlertMessageBuilder setLongitude(double longitude);

}
