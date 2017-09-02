package it.unibo.drescue.model;

import java.sql.Timestamp;

public class OccurredEventImplBuilder {

    private Timestamp timestamp = new Timestamp(0);
    private double latitude = 0;
    private double longitude = 0;
    private String description = "";
    private int eventID = 0;
    private String cpID = "";

    public OccurredEventImplBuilder setTimestamp(final Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public OccurredEventImplBuilder setLatitude(final double latitude) {
        this.latitude = latitude;
        return this;
    }

    public OccurredEventImplBuilder setLongitude(final double longitude) {
        this.longitude = longitude;
        return this;
    }

    public OccurredEventImplBuilder setDescription(final String description) {
        this.description = description;
        return this;
    }

    public OccurredEventImplBuilder setEventID(final int eventID) {
        this.eventID = eventID;
        return this;
    }

    public OccurredEventImplBuilder setCpID(final String cpID) {
        this.cpID = cpID;
        return this;
    }

    public OccurredEventImpl createOccurredEvent() {
        return new OccurredEventImpl(this.timestamp, this.latitude, this.longitude, this.description, this.eventID, this.cpID);
    }

}
