package it.unibo.drescue.communication;

import it.unibo.drescue.communication.messages.response.ErrorMessageImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GsonUtilsTest {

    private static final String ERROR_STRING = "{\"error\":\"test\"}";

    private static final String ERROR_MESSAGE = "{\"error\":\"test\",\"messageType\":\"error_message\"}";

    @Test
    public void fromStringToObject() {
        final ErrorMessageImpl errorMessage = new ErrorMessageImpl("test");
        assertEquals(GsonUtils.fromGson(ERROR_STRING, ErrorMessageImpl.class).getError(),
                errorMessage.getError());
    }

    @Test
    public void fromObjectToString() {
        final ErrorMessageImpl errorMessage = new ErrorMessageImpl("test");
        assertEquals(ERROR_MESSAGE, GsonUtils.toGson(errorMessage));
    }

}
