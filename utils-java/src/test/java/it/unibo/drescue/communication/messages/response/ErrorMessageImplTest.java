package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.MessageType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ErrorMessageImplTest {

    private static final String ERROR = "test error";
    private ErrorMessageImpl errorMessage;

    @Before
    public void createErrorMessage() {
        this.errorMessage = new ErrorMessageImpl(ERROR);
    }

    @Test
    public void checkCorrectError() {
        final String error = this.errorMessage.getError();
        final String messageType = this.errorMessage.getMessageType();
        assertEquals(error, ERROR);
        assertEquals(messageType, MessageType.ERROR_MESSAGE.getMessageType());
    }

}
