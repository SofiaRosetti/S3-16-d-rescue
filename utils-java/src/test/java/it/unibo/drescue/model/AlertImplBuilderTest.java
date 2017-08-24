package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AlertImplBuilderTest {

    private static final double LATITUDE = 44.139773;
    private static final double LONGITUDE = 12.243283;
    private AlertImpl alert;

    @Before
    public void createAlertWithLatLng() throws Exception {
        this.alert = new AlertImplBuilder()
                .setLatitude(this.LATITUDE)
                .setLongitude(this.LONGITUDE)
                .createAlertImpl();
    }

    @Test
    public void checkCorrectLatitude() throws Exception {
        final double latitude = this.alert.getLatitude();
        assertTrue(latitude == this.LATITUDE);
    }

    @Test
    public void checkCorrectLongitude() throws Exception {
        final double longitude = this.alert.getLongitude();
        assertTrue(longitude == this.LONGITUDE);
    }

    @Test
    public void checkNonSetFields() throws Exception {
        final int userID = this.alert.getUserID();
        final int eventID = this.alert.getEventID();
        final int districtID = this.alert.getDistrictID();
        assertTrue(userID == 0 && eventID == 0 && districtID == 0);
    }

}