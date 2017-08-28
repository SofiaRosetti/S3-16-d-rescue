package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EventTypeImplTest {

    private static final int EVENT_ID = 1;
    private static final String EVENT_NAME = "Earthquake";
    private EventType eventType;

    @Before
    public void createEventType() throws Exception {
        this.eventType = new EventTypeImpl(this.EVENT_ID, this.EVENT_NAME);
    }

    @Test
    public void checkEventTypeCreation() throws Exception {
        final int eventID = this.eventType.getEventID();
        final String eventName = this.eventType.getEventName();
        assertTrue(eventID == this.EVENT_ID && eventName.equals(this.EVENT_NAME));
    }

}