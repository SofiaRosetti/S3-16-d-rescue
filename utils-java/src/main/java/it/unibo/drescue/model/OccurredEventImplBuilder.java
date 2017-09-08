package it.unibo.drescue.model;

import java.sql.Timestamp;

public class OccurredEventImplBuilder {

    private final OccurredEventImpl event;

    public OccurredEventImplBuilder() {
        this.event = new OccurredEventImpl();
    }

    public OccurredEventImplBuilder setTimestamp(final Timestamp timestamp) {
        this.event.setTimestamp(timestamp);
        return this;
    }

    public OccurredEventImplBuilder setLatitude(final double latitude) {
        this.event.setLatitude(latitude);
        return this;
    }

    public OccurredEventImplBuilder setLongitude(final double longitude) {
        this.event.setLongitude(longitude);
        return this;
    }

    public OccurredEventImplBuilder setDescription(final String description) {
        this.event.setDescription(description);
        return this;
    }

    public OccurredEventImplBuilder setEventID(final int eventID) {
        this.event.setEventID(eventID);
        return this;
    }

    public OccurredEventImplBuilder setCpID(final String cpID) {
        this.event.setCpID(cpID);
        return this;
    }

    public OccurredEventImpl createOccurredEvent() {
        return this.event;
    }

}
