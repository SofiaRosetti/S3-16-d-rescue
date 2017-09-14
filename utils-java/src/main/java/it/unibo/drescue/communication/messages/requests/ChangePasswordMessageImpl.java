package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.messages.AbstractMessage;
import it.unibo.drescue.communication.messages.MessageType;

/**
 * Class that represent a message for performing the request to change password.
 */
public class ChangePasswordMessageImpl extends AbstractMessage implements ChangePasswordMessage {

    private String email;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordMessageImpl() {
        super(MessageType.CHANGE_PASSWORD_MESSAGE);
    }

    @Override
    public String getUserEmail() {
        return this.email;
    }

    @Override
    public void setUserEmail(final String userEmail) {
        this.email = userEmail;
    }

    @Override
    public String getOldPassword() {
        return this.oldPassword;
    }

    @Override
    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public String getNewPassword() {
        return this.newPassword;
    }

    @Override
    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }
}
