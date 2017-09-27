package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
                .setRescueTeamID(RESCUETEAM_ID)
                .setPassword(PASSWORD)
                .setName(NAME)
                .setLatitude(LATITUDE)
                .setLongitude(LONGITUDE)
                .setPhoneNumber(PHONE_NUMBER)
                .createRescueTeamImpl();
    }

    @Test
    public void checkCorrectID() throws Exception {
        final String rescueTeamID = this.team.getRescueTeamID();
        assertEquals(rescueTeamID, RESCUETEAM_ID);
    }

    @Test
    public void checkCorrectPassword() throws Exception {
        final String password = this.team.getPassword();
        assertEquals(password, PASSWORD);
    }

    @Test
    public void checkCorrectName() throws Exception {
        final String name = this.team.getName();
        assertEquals(name, NAME);
    }

    @Test
    public void checkCorrectLatLng() throws Exception {
        final double latitude = this.team.getLatitude();
        final double longitude = this.team.getLongitude();
        assertTrue(latitude == LATITUDE && longitude == LONGITUDE);
    }

    @Test
    public void checkCorrectPhoneNumber() throws Exception {
        final String phoneNumber = this.team.getPhoneNumber();
        assertEquals(phoneNumber, PHONE_NUMBER);
    }

}