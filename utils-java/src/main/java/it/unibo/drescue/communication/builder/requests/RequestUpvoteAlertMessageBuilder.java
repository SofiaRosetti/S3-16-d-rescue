package it.unibo.drescue.communication.builder.requests;

import it.unibo.drescue.communication.builder.MessageBuilder;

/**
 * Builder interface for request upvote alert messages.
 */
public interface RequestUpvoteAlertMessageBuilder extends MessageBuilder {

    /**
     * @param userID user identifier
     * @return the builder with the given userID
     */
    RequestUpvoteAlertMessageBuilder setUserID(int userID);

    /**
     * @param alertID alert identifier
     * @return the builder with the given alertID
     */
    RequestUpvoteAlertMessageBuilder setAlertID(int alertID);

    /**
     * @param districtID district identifier
     * @return the builder with the given districtID
     */
    RequestUpvoteAlertMessageBuilder setDistrictID(String districtID);

}
