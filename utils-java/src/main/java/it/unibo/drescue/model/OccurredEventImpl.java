package it.unibo.drescue.model;

import java.sql.Timestamp;

/**
 * Implementation of an event that really happened
 */
public class OccurredEventImpl implements OccurredEvent {

    private final Timestamp timestamp;
    private final double latitude;
    private final double longitude;
    private final String description;
    private final int eventID;
    private final String cpID;

    public OccurredEventImpl(final Timestamp timestamp, final double latitude, final double longitude, final String description, final int eventID, final String cpID) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.eventID = eventID;
        this.cpID = cpID;
    }

    @Override
    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    @Override
    public double getLatitude() {
        return this.latitude;
    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getEventID() {
        return this.eventID;
    }

    @Override
    public String getCpID() {
        return this.cpID;
    }
}
