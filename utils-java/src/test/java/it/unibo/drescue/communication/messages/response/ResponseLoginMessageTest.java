package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.MessageType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseLoginMessageTest {

    private static final int USER_ID = 12345;

    private ResponseLoginMessageImpl responseLoginMessage;

    @Before
    public void init() {
        this.responseLoginMessage = new ResponseLoginMessageImpl(USER_ID);
    }

    @Test
    public void checkCorrectUserID() {
        assertTrue(this.responseLoginMessage.getUserID() == USER_ID);
    }

    @Test
    public void checkCorrectMessageType() {
        assertEquals(MessageType.RESPONSE_LOGIN_MESSAGE.getMessageType(), this.responseLoginMessage.getMessageType());
    }

}
