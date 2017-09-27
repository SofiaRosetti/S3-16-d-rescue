package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlertImplBuilderTest {

    private static final int ALERT_ID = 1558;
    private static final double LATITUDE = 44.139773;
    private static final double LONGITUDE = 12.243283;
    private Alert alert;

    @Before
    public void createAlertWithLatLng() throws Exception {
        this.alert = new AlertImplBuilder()
                .setAlertID(ALERT_ID)
                .setLatitude(LATITUDE)
                .setLongitude(LONGITUDE)
                .createAlertImpl();
    }

    @Test
    public void checkCorrectID() throws Exception {
        final int alertID = this.alert.getAlertID();
        assertEquals(alertID, ALERT_ID);
    }

    @Test
    public void checkCorrectLatitude() throws Exception {
        final double latitude = this.alert.getLatitude();
        assertTrue(latitude == LATITUDE);
    }

    @Test
    public void checkCorrectLongitude() throws Exception {
        final double longitude = this.alert.getLongitude();
        assertTrue(longitude == LONGITUDE);
    }

    @Test
    public void checkNonSetFields() throws Exception {
        final int userID = this.alert.getUserID();
        final String eventName = this.alert.getEventName();
        final int upvotes = this.alert.getUpvotes();
        final String districtID = this.alert.getDistrictID();
        assertTrue(userID == 0 && eventName == null && upvotes == 0);
        assertNull(districtID);
    }

}