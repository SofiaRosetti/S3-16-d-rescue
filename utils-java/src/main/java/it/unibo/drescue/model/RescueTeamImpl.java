package it.unibo.drescue.model;

/**
 * Rescue Team implementation
 */
public class RescueTeamImpl implements RescueTeam {

    private String rescueTeamID;
    private String password;
    private String name;
    private double latitude;
    private double longitude;
    private String phoneNumber;

    public RescueTeamImpl() {
    }

    @Override
    public String getRescueTeamID() {
        return this.rescueTeamID;
    }

    @Override
    public void setRescueTeamID(final String rescueTeamID) {
        this.rescueTeamID = rescueTeamID;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
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
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
