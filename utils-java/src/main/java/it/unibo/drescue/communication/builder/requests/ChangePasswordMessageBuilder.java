package it.unibo.drescue.communication.builder.requests;

import it.unibo.drescue.communication.builder.MessageBuilder;

/**
 * Builder interface for change password messages.
 */
public interface ChangePasswordMessageBuilder extends MessageBuilder {

    /**
     * @param userID user identifier
     * @return the builder with the given user identifier
     */
    ChangePasswordMessageBuilder setUserID(int userID);

    /**
     * @param oldPassword user's old password
     * @return the builder with the given user's old password
     */
    ChangePasswordMessageBuilder setOldPassword(String oldPassword);

    /**
     * @param newPassword user's new password
     * @return the builder with the given user's new password
     */
    ChangePasswordMessageBuilder setNewPassword(String newPassword);

}
