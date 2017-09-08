package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.MessageType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UpvoteAlertMessageTest {

    private static final int ALERT_ID = 12345;
    private static final int ALERT_UPVOTE_NUMBER = 201;

    private UpvoteAlertMessageImpl upvoteAlertMessage;

    @Before
    public void init() {
        this.upvoteAlertMessage = new UpvoteAlertMessageImpl(ALERT_ID, ALERT_UPVOTE_NUMBER);
    }

    @Test
    public void checkCorrectAlertID() {
        assertTrue(this.upvoteAlertMessage.getAlertID() == ALERT_ID);
    }

    @Test
    public void checkCorrectTotalUpvote() {
        assertTrue(this.upvoteAlertMessage.getUpvoteTotal() == ALERT_UPVOTE_NUMBER);
    }

    @Test
    public void checkCorrectMessageType() {
        assertEquals(MessageType.UPVOTE_MESSAGE.getMessageType(), this.upvoteAlertMessage.getMessageType());
    }

}
