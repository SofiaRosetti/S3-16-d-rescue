package it.unibo.drescue.model;

import java.sql.Timestamp;

public class AlertImplBuilder {

    private Timestamp timestamp = new Timestamp(0);
    private double latitude = 0;
    private double longitude = 0;
    private int userID = 0;
    private int eventID = 0;
    private int districtID = 0;

    public AlertImplBuilder setTimestamp(final Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public AlertImplBuilder setLatitude(final double latitude) {
        this.latitude = latitude;
        return this;
    }

    public AlertImplBuilder setLongitude(final double longitude) {
        this.longitude = longitude;
        return this;
    }

    public AlertImplBuilder setUserID(final int userID) {
        this.userID = userID;
        return this;
    }

    public AlertImplBuilder setEventID(final int eventID) {
        this.eventID = eventID;
        return this;
    }

    public AlertImplBuilder setDistrictID(final int districtID) {
        this.districtID = districtID;
        return this;
    }

    public AlertImpl createAlertImpl() {
        return new AlertImpl(this.timestamp, this.latitude, this.longitude, this.userID, this.eventID, this.districtID);
    }
}
