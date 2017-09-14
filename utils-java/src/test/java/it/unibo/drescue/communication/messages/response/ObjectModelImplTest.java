package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.model.AlertImpl;
import it.unibo.drescue.model.AlertImplBuilder;
import it.unibo.drescue.model.ObjectModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObjectModelImplTest {

    private ObjectModelMessageImpl objectModelMessage;
    private AlertImpl alert;

    @Before
    public void createObjectModelMessage() {
        this.alert = new AlertImplBuilder().createAlertImpl();
        this.objectModelMessage = new ObjectModelMessageImpl(this.alert);
    }

    @Test
    public void checkCorrectObjectModel() {
        final String messageType = this.objectModelMessage.getMessageType();
        final ObjectModel objectModel = this.objectModelMessage.getObjectModel();
        assertEquals(messageType, MessageType.OBJECT_MODEL_MESSAGE.getMessageType());
        assertEquals(objectModel, this.alert);
    }

}
