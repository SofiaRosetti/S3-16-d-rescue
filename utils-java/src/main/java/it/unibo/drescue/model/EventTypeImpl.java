package it.unibo.drescue.model;

/**
 * Event type implementation
 */
public class EventTypeImpl implements EventType {

    private String eventName;

    public EventTypeImpl(final String eventName) {
        this.eventName = eventName;
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
