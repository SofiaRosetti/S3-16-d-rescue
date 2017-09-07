package it.unibo.drescue.model;

public class RescueTeamImplBuilder {

    private final RescueTeamImpl team;

    public RescueTeamImplBuilder() {
        this.team = new RescueTeamImpl();
    }

    public RescueTeamImplBuilder setRescueTeamID(final String rescueTeamID) {
        this.team.setRescueTeamID(rescueTeamID);
        return this;
    }

    public RescueTeamImplBuilder setPassword(final String password) {
        this.team.setPassword(password);
        return this;
    }

    public RescueTeamImplBuilder setName(final String name) {
        this.team.setName(name);
        return this;
    }

    public RescueTeamImplBuilder setLatitude(final double latitude) {
        this.team.setLatitude(latitude);
        return this;
    }

    public RescueTeamImplBuilder setLongitude(final double longitude) {
        this.team.setLongitude(longitude);
        return this;
    }

    public RescueTeamImplBuilder setPhoneNumber(final String phoneNumber) {
        this.team.setPhoneNumber(phoneNumber);
        return this;
    }

    public RescueTeamImpl createRescueTeamImpl() {
        return this.team;
    }
}
