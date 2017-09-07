package it.unibo.drescue.communication.builder.requests;

import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.requests.ChangePasswordMessageImpl;

/**
 * Builder class for request message to change password.
 */
public class ChangePasswordMessageBuilderImpl implements ChangePasswordMessageBuilder {

    private final ChangePasswordMessageImpl message;

    public ChangePasswordMessageBuilderImpl() {
        this.message = new ChangePasswordMessageImpl();
    }

    @Override
    public ChangePasswordMessageBuilder setUserID(final int userID) {
        this.message.setUserID(userID);
        return this;
    }

    @Override
    public ChangePasswordMessageBuilder setOldPassword(final String oldPassword) {
        this.message.setOldPassword(oldPassword);
        return this;
    }

    @Override
    public ChangePasswordMessageBuilder setNewPassword(final String newPassword) {
        this.message.setNewPassword(newPassword);
        return this;
    }

    @Override
    public Message build() {
        return this.message;
    }
}
