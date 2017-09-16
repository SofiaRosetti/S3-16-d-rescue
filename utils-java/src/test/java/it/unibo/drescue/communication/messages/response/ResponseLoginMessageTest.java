package it.unibo.drescue.communication.messages.response;

import it.unibo.drescue.communication.messages.MessageType;
import it.unibo.drescue.model.EventTypeImpl;
import it.unibo.drescue.model.UserImpl;
import it.unibo.drescue.model.UserImplBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseLoginMessageTest {

    private static final int USER_ID = 12345;
    private static final String USER_NAME = "John";
    private static final String USER_SURNAME = "Doe";
    private static final String USER_EMAIL = "john.doe@test.com";
    private static final String USER_PHONE = "3332244556";
    private static final String EVENT_TYPE_1 = "Fire";
    private static final String EVENT_TYPE_2 = "Earthquake";

    private UserImpl user;
    private List<EventTypeImpl> eventTypes;
    private ResponseLoginMessageImpl responseLoginMessage;

    @Before
    public void init() {
        this.user = new UserImplBuilder()
                .setUserID(USER_ID)
                .setName(USER_NAME)
                .setSurname(USER_SURNAME)
                .setEmail(USER_EMAIL)
                .setPhoneNumber(USER_PHONE)
                .createUserImpl();
        this.eventTypes = new ArrayList<>();
        this.eventTypes.add(new EventTypeImpl(EVENT_TYPE_1));
        this.eventTypes.add(new EventTypeImpl(EVENT_TYPE_2));
        this.responseLoginMessage = new ResponseLoginMessageImpl(this.user, this.eventTypes);
    }

    @Test
    public void checkCorrectUser() {
        assertEquals(this.user, this.responseLoginMessage.getUser());
    }

    @Test
    public void checkCorrectUserID() {
        assertTrue(this.responseLoginMessage.getUser().getUserID() == USER_ID);
    }

    @Test
    public void checkCorrectUserEmail() {
        assertEquals(USER_EMAIL, this.responseLoginMessage.getUser().getEmail());
    }

    @Test
    public void checkPasswordNoPresent() {
        assertEquals(null, this.responseLoginMessage.getUser().getPassword());
    }

    @Test
    public void checkCorrectListEventTypes() {
        assertEquals(this.eventTypes, this.responseLoginMessage.getEventsType());
    }

    @Test
    public void checkCorrectFirstEventType() {
        assertEquals(this.eventTypes.get(0).getEventName(),
                this.responseLoginMessage.getEventsType().get(0).getEventName());
    }

    @Test
    public void checkCorrectMessageType() {
        assertEquals(MessageType.RESPONSE_LOGIN_MESSAGE.getMessageType(),
                this.responseLoginMessage.getMessageType());
    }

}
