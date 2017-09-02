package it.unibo.drescue.communication.messages.response;

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
        assertEquals(error, ERROR);
    }

}
