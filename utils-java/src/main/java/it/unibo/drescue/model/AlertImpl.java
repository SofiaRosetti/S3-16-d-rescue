package it.unibo.drescue.model;

import java.sql.Timestamp;

/**
 * Alert implementation
 */
public class AlertImpl implements Alert {

    private final int alertID;
    private final Timestamp timestamp;
    private final double latitude;
    private final double longitude;
    private final int userID;
    private final int eventID;
    private final String districtID;
    private final int upvotes;

    public AlertImpl(final int alertID, final Timestamp timestamp, final double latitude, final double longitude, final int userID, final int eventID, final String districtID, final int upvotes) {
        this.alertID = alertID;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = userID;
        this.eventID = eventID;
        this.districtID = districtID;
        this.upvotes = upvotes;
    }

    @Override
    public int getAlertID() {
        return this.alertID;
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
    public String getDistrictID() {
        return this.districtID;
    }

    @Override
    public int getUpvotes() {
        return this.upvotes;
    }
}
