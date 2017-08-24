package it.unibo.drescue.model;

import java.sql.Timestamp;

/**
 * Alert implementation
 */
public class AlertImpl implements Alert {

    private final Timestamp timestamp;
    private final double latitude;
    private final double longitude;
    private final int userID;
    private final int eventID;
    private final int districtID;

    public AlertImpl(final Timestamp timestamp, final double latitude, final double longitude, final int userID, final int eventID, final int districtID) {
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = userID;
        this.eventID = eventID;
        this.districtID = districtID;
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
    public int getUserID() {
        return this.userID;
    }

    @Override
    public int getEventID() {
        return this.eventID;
    }

    @Override
    public int getDistrictID() {
        return this.districtID;
    }
}
