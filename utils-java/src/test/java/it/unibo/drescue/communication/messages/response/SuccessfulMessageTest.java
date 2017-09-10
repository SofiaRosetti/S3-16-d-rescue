package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.MessageType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SuccessfulMessageTest {

    private SuccessfulMessageImpl successfulMessageImpl;

    @Before
    public void createErrorMessage() {
        this.successfulMessageImpl = new SuccessfulMessageImpl();
    }

    @Test
    public void checkCorrectSuccessfulMessage() {
        assertEquals(MessageType.SUCCESSFUL_MESSAGE.getMessageType(), this.successfulMessageImpl.getMessageType());

    }
}
