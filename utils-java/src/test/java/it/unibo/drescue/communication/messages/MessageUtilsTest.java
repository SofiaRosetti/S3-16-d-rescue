package it.unibo.drescue.communication.messages;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageUtilsTest {

    private static final String MESSAGE_CORRECT = "alerts_message";
    private static final String MESSAGE_UNKNOWN = "message_test";

    @Test
    public void isACorrectMessageType() {
        assertEquals(MessageType.ALERTS_MESSAGE, MessageUtils.getMessageNameByType(MESSAGE_CORRECT));
    }

    @Test
    public void isAnUnknownMessageType() {
        assertEquals(MessageType.UNKNOWN_MESSAGE, MessageUtils.getMessageNameByType(MESSAGE_UNKNOWN));
    }
}
