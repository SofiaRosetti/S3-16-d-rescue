package it.unibo.drescue.model;

import java.sql.Timestamp;

public class AlertImplBuilder {

    private final AlertImpl alert;

    public AlertImplBuilder() {
        this.alert = new AlertImpl();
    }

    public AlertImplBuilder setAlertID(final int alertID) {
        this.alert.setAlertID(alertID);
        return this;
    }

    public AlertImplBuilder setTimestamp(final Timestamp timestamp) {
        this.alert.setTimestamp(timestamp);
        return this;
    }

    public AlertImplBuilder setLatitude(final double latitude) {
        this.alert.setLatitude(latitude);
        return this;
    }

    public AlertImplBuilder setLongitude(final double longitude) {
        this.alert.setLongitude(longitude);
        return this;
    }

    public AlertImplBuilder setUserID(final int userID) {
        this.alert.setUserID(userID);
        return this;
    }

    public AlertImplBuilder setEventName(final String eventName) {
        this.alert.setEventName(eventName);
        return this;
    }

    public AlertImplBuilder setDistrictID(final String districtID) {
        this.alert.setDistrictID(districtID);
        return this;
    }

    public AlertImplBuilder setUpvotes(final int upvotes) {
        this.alert.setUpvotes(upvotes);
        return this;
    }

    public AlertImpl createAlertImpl() {
        return this.alert;
    }
}
