package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.builder.requests.ChangePasswordMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChangePasswordMessageTest {

    private static final int USER_ID = 12345;
    private static final String USER_OLD_PASSWORD = "test";
    private static final String USER_NEW_PASSWORD = "0000";

    private ChangePasswordMessage changePasswordMessage;

    /**
     * Creates a test message containing the identifier and the old password of a test user.
     */
    @Before
    public void init() {
        final Message message = new ChangePasswordMessageBuilderImpl()
                .setUserID(USER_ID)
                .setOldPassword(USER_OLD_PASSWORD)
                .build();

        if (message.getMessageType().equals(MessageType.CHANGE_PASSWORD_MESSAGE.getMessageType())) {
            this.changePasswordMessage = (ChangePasswordMessageImpl) message;
        }
    }

    @Test
    public void isUserIDSet() {
        assertTrue(this.changePasswordMessage.getUserID() == USER_ID);
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
