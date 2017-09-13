package it.unibo.drescue.model;

import java.sql.Timestamp;

/**
 * Alert implementation
 */
public class AlertImpl implements Alert {

    private int alertID;
    private Timestamp timestamp;
    private double latitude;
    private double longitude;
    private int userID;
    private String eventName;
    private String districtID;
    private int upvotes;

    public AlertImpl() {

    }

    @Override
    public int getAlertID() {
        return this.alertID;
    }

    @Override
    public void setAlertID(final int alertID) {
        this.alertID = alertID;
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
    public int getUserID() {
        return this.userID;
    }

    @Override
    public void setUserID(final int userID) {
        this.userID = userID;
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
    public String getDistrictID() {
        return this.districtID;
    }

    @Override
    public void setDistrictID(final String districtID) {
        this.districtID = districtID;
    }

    @Override
    public int getUpvotes() {
        return this.upvotes;
    }

    @Override
    public void setUpvotes(final int upvotes) {
        this.upvotes = upvotes;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        final String newLine = System.getProperty("line.separator");

        result.append("Alert: {" + newLine);
        result.append(" Alert ID: " + this.getAlertID() + newLine);
        result.append(" Timestamp: " + this.getTimestamp() + newLine);
        result.append(" Latitude: " + this.getLatitude() + newLine);
        result.append(" Longitude: " + this.getLongitude() + newLine);
        result.append(" User ID: " + this.getUserID() + newLine);
        result.append(" Event name: " + this.getEventName() + newLine);
        result.append(" District ID: " + this.getDistrictID() + newLine);
        result.append(" Upvotes: " + this.getUpvotes() + newLine);
        result.append("}");

        return result.toString();
    }

    @Override
    public String toPrintableString() {
        final StringBuilder result = new StringBuilder();
        final String space = " ";

        result.append("Alert:" + space);
        result.append(this.getTimestamp() + space);
        result.append(this.getEventName() + space);
        result.append(this.getLatitude() + space);
        result.append(this.getLongitude() + space);
        result.append(this.getUpvotes() + space);

        return result.toString();
    }
}
