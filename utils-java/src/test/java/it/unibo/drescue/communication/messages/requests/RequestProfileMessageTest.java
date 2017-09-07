package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the creation of a correct request message for user data.
 */
public class RequestProfileMessageTest {

    private static final int USER_ID = 12345;

    private RequestProfileMessage requestProfileMessageImpl;

    /**
     * Creates a test message containing the identifier of a test user.
     */
    @Before
    public void init() {
        final Message requestProfileMessage = new RequestProfileMessageImpl(USER_ID);
        if (requestProfileMessage.getMessageType().equals(MessageType.REQUEST_PROFILE_MESSAGE.getMessageType())) {
            this.requestProfileMessageImpl = (RequestProfileMessageImpl) requestProfileMessage;
        }
    }

    @Test
    public void checkCorrectUserID() {
        assertEquals(USER_ID, this.requestProfileMessageImpl.getUserID());
    }

}
