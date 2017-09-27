package it.unibo.drescue.model;

import java.sql.Timestamp;

/**
 * Implementation of an event that really happened
 */
public class OccurredEventImpl implements OccurredEvent {

    private Timestamp timestamp;
    private double latitude;
    private double longitude;
    private String description;
    private int eventID;
    private String cpID;

    public OccurredEventImpl() {
    }

    @Override
    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    @Override
    public void setTimestamp(final Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public double getLatitude() {
        return this.latitude;
    }

    @Override
    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }

    @Override
    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
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
    public String getCpID() {
        return this.cpID;
    }

    @Override
    public void setCpID(final String cpID) {
        this.cpID = cpID;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        final String newLine = System.getProperty("line.separator");

        result.append("Occurred event: {" + newLine);
        result.append(" Timestamp: " + this.getCpID() + newLine);
        result.append(" Latitude: " + this.getLatitude() + newLine);
        result.append(" Longitude: " + this.getLongitude() + newLine);
        result.append(" Description: " + this.getDescription() + newLine);
        result.append(" Event ID: " + this.getEventID() + newLine);
        result.append(" Cp ID: " + this.getCpID() + newLine);

        result.append("}");

        return result.toString();
    }
}
