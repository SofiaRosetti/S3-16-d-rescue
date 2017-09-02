package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RescueTeamImplBuilderTest {

    private static final String RESCUETEAM_ID = "Team001";
    private static final String PASSWORD = "fddhj73bee";
    private static final String NAME = "Rescue Team 001";
    private static final double LATITUDE = 44.139773;
    private static final double LONGITUDE = 12.243283;
    private static final String PHONE_NUMBER = "3689994751";
    private RescueTeam team;

    @Before
    public void createRescueTeam() throws Exception {
        this.team = new RescueTeamImplBuilder()
                .setRescueTeamID(this.RESCUETEAM_ID)
                .setPassword(this.PASSWORD)
                .setName(this.NAME)
                .setLatitude(this.LATITUDE)
                .setLongitude(this.LONGITUDE)
                .setPhoneNumber(this.PHONE_NUMBER)
                .createRescueTeamImpl();
    }

    @Test
    public void checkCorrectID() throws Exception {
        final String rescueTeamID = this.team.getRescueTeamID();
        assertTrue(rescueTeamID.equals(this.RESCUETEAM_ID));
    }

    @Test
    public void checkCorrectPassword() throws Exception {
        final String password = this.team.getPassword();
        assertTrue(password.equals(this.PASSWORD));
    }

    @Test
    public void checkCorrectName() throws Exception {
        final String name = this.team.getName();
        assertTrue(name.equals(this.NAME));
    }

    @Test
    public void checkCorrectLatLng() throws Exception {
        final double latitude = this.team.getLatitude();
        final double longitude = this.team.getLongitude();
        assertTrue(latitude == this.LATITUDE && longitude == this.LONGITUDE);
    }

    @Test
    public void checkCorrectPhoneNumber() throws Exception {
        final String phoneNumber = this.team.getPhoneNumber();
        assertTrue(phoneNumber.equals(this.PHONE_NUMBER));
    }

}