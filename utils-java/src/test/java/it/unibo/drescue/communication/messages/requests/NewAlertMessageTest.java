package it.unibo.drescue.communication.messages.requests;

import it.unibo.drescue.communication.builder.requests.NewAlertMessageBuilder;
import it.unibo.drescue.communication.builder.requests.NewAlertMessageBuilderImpl;
import it.unibo.drescue.communication.messages.Message;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class NewAlertMessageTest {


    private static final int USER_ID = 10;
    private static final int EVENT_TYPE = 10;

    private NewAlertMessageImpl newAlertMessageImpl;

    @Before
    public void build(){

        final Message newAlerMessage = new NewAlertMessageBuilderImpl()
                .setUserID(USER_ID)
                .setEventType(EVENT_TYPE)
                .build();

        if (newAlerMessage.getMessageType().equals(NewAlertMessageImpl.NEW_ALERT_MESSAGE)){
          newAlertMessageImpl = (NewAlertMessageImpl) newAlerMessage;
        }
    }

    @Test
    public void checkIfSetUserIDAndEventType() {
        assertEquals(USER_ID, this.newAlertMessageImpl.getUserID());
        assertEquals(EVENT_TYPE, this.newAlertMessageImpl.getEventType());
    }

    @Test
    public void checkNotSetFields() {
        assertNotEquals(null, this.newAlertMessageImpl.getLatitude());
        assertNotEquals(null, this.newAlertMessageImpl.getLongitude());
        //(0.0,this.newAlertMessageImpl.getLatitude(), 0);
    }



}
