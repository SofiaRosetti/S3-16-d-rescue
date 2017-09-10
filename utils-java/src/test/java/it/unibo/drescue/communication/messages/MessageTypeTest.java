package it.unibo.drescue.communication.messages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the enum of message type.
 */
public class MessageTypeTest {

    private static final String MESSAGE = "alerts_message";
    private static final String TYPE = "ALERTS_MESSAGE";

    @Test
    public void checkCorrectMessageType() {
        assertEquals(MESSAGE, MessageType.ALERTS_MESSAGE.getMessageType());
    }

    @Test
    public void checkCorrectValueType() {
        assertEquals(MessageType.ALERTS_MESSAGE, MessageType.valueOf(TYPE));
    }

}
