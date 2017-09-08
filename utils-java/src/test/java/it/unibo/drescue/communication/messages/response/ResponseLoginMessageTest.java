package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.model.User;
import it.unibo.drescue.model.UserImplBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseLoginMessageTest {

    private static final int USER_ID = 12345;
    private static final String USER_NAME = "John";
    private static final String USER_SURNAME = "Doe";
    private static final String USER_EMAIL = "john.doe@test.com";
    private static final String USER_PHONE = "3332244556";

    private User user;
    private ResponseLoginMessageImpl responseLoginMessage;

    @Before
    public void init() {
        this.user = new UserImplBuilder()
                .setUserID(USER_ID)
                .setName(USER_NAME)
                .setSurname(USER_SURNAME)
                .setEmail(USER_EMAIL)
                .setPhoneNumber(USER_PHONE)
                .createUserImpl();
        this.responseLoginMessage = new ResponseLoginMessageImpl(this.user);
    }

    @Test
    public void checkCorrectUser() {
        assertEquals(this.user, this.responseLoginMessage.getUser());
    }

    @Test
    public void checkCorrectUserID() {
        assertTrue(this.responseLoginMessage.getUser().getUserID() == USER_ID);
    }

    @Test
    public void checkCorrectUserEmail() {
        assertEquals(USER_EMAIL, this.responseLoginMessage.getUser().getEmail());
    }

    @Test
    public void checkPasswordNoPresent() {
        assertEquals(null, this.responseLoginMessage.getUser().getPassword());
    }

    @Test
    public void checkCorrectMessageType() {
        assertEquals(MessageType.RESPONSE_LOGIN_MESSAGE.getMessageType(),
                this.responseLoginMessage.getMessageType());
    }

}
