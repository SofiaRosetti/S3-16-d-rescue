package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.builder.requests.ChangePasswordMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChangePasswordMessageTest {

    private static final String USER_EMAIL = "j.doe@test.com";
    private static final String USER_OLD_PASSWORD = "test";
    private static final String USER_NEW_PASSWORD = "0000";

    private ChangePasswordMessage changePasswordMessage;

    /**
     * Creates a test message containing the identifier and the old password of a test user.
     */
    @Before
    public void init() {
        final Message message = new ChangePasswordMessageBuilderImpl()
                .setUserEmail(USER_EMAIL)
                .setOldPassword(USER_OLD_PASSWORD)
                .build();

        if (message.getMessageType().equals(MessageType.CHANGE_PASSWORD_MESSAGE.getMessageType())) {
            this.changePasswordMessage = (ChangePasswordMessageImpl) message;
        }
    }

    @Test
    public void isUserIDSet() {
        assertEquals(USER_EMAIL, this.changePasswordMessage.getUserEmail());
    }

    @Test
    public void isOldPasswordSet() {
        assertEquals(USER_OLD_PASSWORD, this.changePasswordMessage.getOldPassword());
    }

    @Test
    public void isNewPasswordUnset() {
        assertEquals(null, this.changePasswordMessage.getNewPassword());
    }

    @Test
    public void isNewPasswordSet() {
        this.changePasswordMessage.setNewPassword(USER_NEW_PASSWORD);
        assertEquals(USER_NEW_PASSWORD, this.changePasswordMessage.getNewPassword());
    }


}
