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

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        final String newLine = System.getProperty("line.separator");

        result.append("Event type: {" + newLine);
        result.append(" Event name: " + this.getEventName() + newLine);
        result.append("}");

        return result.toString();
    }

}
