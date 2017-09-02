package it.unibo.drescue.model;

public class RescueTeamImplBuilder {

    private String rescueTeamID = "";
    private String password = "";
    private String name = "";
    private double latitude = 0;
    private double longitude = 0;
    private String phoneNumber = "";

    public RescueTeamImplBuilder setRescueTeamID(final String rescueTeamID) {
        this.rescueTeamID = rescueTeamID;
        return this;
    }

    public RescueTeamImplBuilder setPassword(final String password) {
        this.password = password;
        return this;
    }

    public RescueTeamImplBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    public RescueTeamImplBuilder setLatitude(final double latitude) {
        this.latitude = latitude;
        return this;
    }

    public RescueTeamImplBuilder setLongitude(final double longitude) {
        this.longitude = longitude;
        return this;
    }

    public RescueTeamImplBuilder setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public RescueTeamImpl createRescueTeamImpl() {
        return new RescueTeamImpl(this.rescueTeamID, this.password, this.name, this.latitude, this.longitude, this.phoneNumber);
    }
}
