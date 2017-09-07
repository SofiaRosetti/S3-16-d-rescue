package it.unibo.drescue.model;

/**
 * Event type implementation
 */
public class EventTypeImpl implements EventType {

    private int eventID;
    private String eventName;

    public EventTypeImpl(final int eventID, final String eventName) {
        this.eventID = eventID;
        this.eventName = eventName;
    }

    @Override
    public int getEventID() {
        return this.eventID;
    }

    @Override
    public void setEventID(final int eventID) {
        this.eventID = eventID;
    }

    @Override
    public String getEventName() {
        return this.eventName;
    }

    @Override
    public void setEventName(final String eventName) {
        this.eventName = eventName;
    }

}
