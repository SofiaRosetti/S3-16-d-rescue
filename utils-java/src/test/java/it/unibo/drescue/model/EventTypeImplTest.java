package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EventTypeImplTest {

    private static final String EVENT_NAME = "Earthquake";
    private EventType eventType;

    @Before
    public void createEventType() throws Exception {
        this.eventType = new EventTypeImpl(EVENT_NAME);
    }

    @Test
    public void checkEventTypeCreation() throws Exception {
        final String eventName = this.eventType.getEventName();
        assertTrue(eventName.equals(EVENT_NAME));
    }

}