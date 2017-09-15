package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.messages.Message;
import it.unibo.drescue.communication.messages.MessageType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestAlertsMessageTest {

    private static final Double LATITUDE = 12.23265;
    private static final Double LONGITUDE = 15.265656;
    private static final Double DELTA = 0.00;

    RequestAlertsMessageImpl requestAlertsMessageImpl;

    @Before
    public void build() {
        final Message requestAlertsMessage = new RequestAlertsMessageImpl(LATITUDE, LONGITUDE).build();

        if (requestAlertsMessage.getMessageType().equals(MessageType.REQUEST_MOBILE_ALERTS_MESSAGE.getMessageType())) {
            this.requestAlertsMessageImpl = (RequestAlertsMessageImpl) requestAlertsMessage;
        }
    }

    @Test
    public void checkCorrectLatitude() throws Exception {
        assertEquals(LATITUDE, this.requestAlertsMessageImpl.getLatitude(), DELTA);
    }

    @Test
    public void checkCorrectLongitude() throws Exception {
        assertEquals(LONGITUDE, this.requestAlertsMessageImpl.getLongitude(), DELTA);
    }


}
