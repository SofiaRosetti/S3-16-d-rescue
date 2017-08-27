package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AlertImplBuilderTest {

    private static final int ALERT_ID = 1558;
    private static final double LATITUDE = 44.139773;
    private static final double LONGITUDE = 12.243283;
    private Alert alert;

    @Before
    public void createAlertWithLatLng() throws Exception {
        this.alert = new AlertImplBuilder()
                .setAlertID(this.ALERT_ID)
                .setLatitude(this.LATITUDE)
                .setLongitude(this.LONGITUDE)
                .createAlertImpl();
    }

    @Test
    public void checkCorrectID() throws Exception {
        final int alertID = this.alert.getAlertID();
        assertTrue(alertID == this.ALERT_ID);
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
        final String districtID = this.alert.getDistrictID();
        assertTrue(userID == 0 && eventID == 0 && districtID.equals(""));
    }

}