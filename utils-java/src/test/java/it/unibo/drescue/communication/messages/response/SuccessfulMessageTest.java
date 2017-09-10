package it.unibo.drescue.communication.messages.response;

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
        assertEquals(SuccessfulMessageImpl.SUCCESSFUL_MESSAGE, this.successfulMessageImpl.getMessageType());

    }
}
