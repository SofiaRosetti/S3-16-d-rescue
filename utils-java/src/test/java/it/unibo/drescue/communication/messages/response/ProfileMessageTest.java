package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.model.User;
import it.unibo.drescue.model.UserImplBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the creation of a correct response message for user data.
 */
public class ProfileMessageTest {

    private static final int USER_ID = 12345;
    private static final String USER_NAME = "John";
    private static final String USER_SURNAME = "Doe";
    private static final String USER_EMAIL = "john.doe@test.com";
    private static final String USER_PASSWORD = "password";
    private static final String USER_PHONE = "3333333333";

    private User user;
    private ProfileMessageImpl profileMessage;

    /**
     * Creates a test message containing user data of a test user.
     */
    @Before
    public void init() {
        this.user = new UserImplBuilder()
                .setUserID(USER_ID)
                .setName(USER_NAME)
                .setSurname(USER_SURNAME)
                .setEmail(USER_EMAIL)
                .setPassword(USER_PASSWORD)
                .setPhoneNumber(USER_PHONE)
                .createUserImpl();

        this.profileMessage = new ProfileMessageImpl(this.user);
    }

    @Test
    public void checkCorrectUser() {
        assertEquals(this.profileMessage.getProfile(), this.user);
    }

    @Test
    public void checkCorrectMessageType() {
        assertEquals(this.profileMessage.getMessageType(), MessageType.PROFILE_MESSAGE.getMessageType());
    }
}
