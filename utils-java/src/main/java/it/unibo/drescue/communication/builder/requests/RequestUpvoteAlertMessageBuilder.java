package it.unibo.drescue.communication.builder.requests;

import it.unibo.drescue.communication.builder.MessageBuilder;

/**
 * Builder interface for request upvote alert messages.
 */
public interface RequestUpvoteAlertMessageBuilder extends MessageBuilder {

    /**
     * @param userID
     * @return the builder with the given userID
     */
    RequestUpvoteAlertMessageBuilder setUserID(int userID);

    /**
     * @param alertID
     * @return the builder with the given alertID
     */
    RequestUpvoteAlertMessageBuilder setAlertID(int alertID);

    /**
     * @param districtID
     * @return the builder with the given districtID
     */
    RequestUpvoteAlertMessageBuilder setDistrictID(String districtID);

}
