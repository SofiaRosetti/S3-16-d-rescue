package it.unibo.drescue.model;

/**
 * Rescue Team implementation
 */
public class RescueTeamImpl implements RescueTeam {

    private final String rescueTeamID;
    private final String password;
    private final String name;
    private final double latitude;
    private final double longitude;
    private final String phoneNumber;

    public RescueTeamImpl(final String rescueTeamID, final String password, final String name, final double latitude, final double longitude, final String phoneNumber) {
        this.rescueTeamID = rescueTeamID;
        this.password = password;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getRescueTeamID() {
        return this.rescueTeamID;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getName() {
        return this.name;
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
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
