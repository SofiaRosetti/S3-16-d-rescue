package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.messages.Message;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginMessageTest {

    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "testPassword";

    private LoginMessageImpl loginMessageImpl;

    @Before
    public void build() {
        final Message loginMessage = new LoginMessageImpl(EMAIL, PASSWORD);

        if (loginMessage.getMessageType().equals(LoginMessageImpl.LOGIN_MESSAGE)) {
            this.loginMessageImpl = (LoginMessageImpl) loginMessage;
        }
    }

    @Test
    public void checkCorrectEmail() throws Exception {
        assertEquals(EMAIL, this.loginMessageImpl.getEmail());
    }

    @Test
    public void checkCorrectPassword() throws Exception {
        assertEquals(PASSWORD, this.loginMessageImpl.getPassword());
    }

}