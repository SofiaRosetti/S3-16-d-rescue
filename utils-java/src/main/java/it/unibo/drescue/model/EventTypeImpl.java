package it.unibo.drescue.model;

/**
 * Event type implementation
 */
public class EventTypeImpl implements EventType {

    private final int eventID;
    private final String eventName;

    public EventTypeImpl(final int eventID, final String eventName) {
        this.eventID = eventID;
        this.eventName = eventName;
    }

    @Override
    public int getEventID() {
        return this.eventID;
    }

    @Override
    public String getEventName() {
        return this.eventName;
    }

}
