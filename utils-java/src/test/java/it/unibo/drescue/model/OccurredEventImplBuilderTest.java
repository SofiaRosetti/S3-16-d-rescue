package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertTrue;

public class OccurredEventImplBuilderTest {

    private static final Timestamp TIMESTAMP = new Timestamp(123456789);
    private static final double LATITUDE = 44.139773;
    private static final double LONGITUDE = 12.243283;
    private static final String DESCRIPTION = "Earthquake of midnight";
    private static final int EVENT_ID = 157;
    private static final String CP_ID = "FC1421";
    private OccurredEvent event;

    @Before
    public void createOccurredEvent() throws Exception {
        this.event = new OccurredEventImplBuilder()
                .setTimestamp(this.TIMESTAMP)
                .setLatitude(this.LATITUDE)
                .setLongitude(this.LONGITUDE)
                .setDescription(this.DESCRIPTION)
                .setEventID(this.EVENT_ID)
                .setCpID(this.CP_ID)
                .createOccurredEvent();
    }

    @Test
    public void checkCorrectTimestamp() throws Exception {
        final Timestamp timestamp = this.event.getTimestamp();
        assertTrue(timestamp.equals(this.TIMESTAMP));
    }

    @Test
    public void checkCorrectLatLng() throws Exception {
        final double latitude = this.event.getLatitude();
        final double longitude = this.event.getLongitude();
        assertTrue(latitude == this.LATITUDE && longitude == this.LONGITUDE);
    }

    @Test
    public void checkCorrectDescription() throws Exception {
        final String description = this.event.getDescription();
        assertTrue(description.equals(this.DESCRIPTION));
    }

    @Test
    public void checkCorrectEventID() throws Exception {
        final int eventID = this.event.getEventID();
        assertTrue(eventID == this.EVENT_ID);
    }

    @Test
    public void checkCorrectCpID() throws Exception {
        final String cpID = this.event.getCpID();
        assertTrue(cpID.equals(this.CP_ID));
    }

}