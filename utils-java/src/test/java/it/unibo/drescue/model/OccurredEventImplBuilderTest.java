package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
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
                .setTimestamp(TIMESTAMP)
                .setLatitude(LATITUDE)
                .setLongitude(LONGITUDE)
                .setDescription(DESCRIPTION)
                .setEventID(EVENT_ID)
                .setCpID(CP_ID)
                .createOccurredEvent();
    }

    @Test
    public void checkCorrectTimestamp() throws Exception {
        final Timestamp timestamp = this.event.getTimestamp();
        assertEquals(timestamp, TIMESTAMP);
    }

    @Test
    public void checkCorrectLatLng() throws Exception {
        final double latitude = this.event.getLatitude();
        final double longitude = this.event.getLongitude();
        assertTrue(latitude == LATITUDE && longitude == LONGITUDE);
    }

    @Test
    public void checkCorrectDescription() throws Exception {
        final String description = this.event.getDescription();
        assertEquals(description, DESCRIPTION);
    }

    @Test
    public void checkCorrectEventID() throws Exception {
        final int eventID = this.event.getEventID();
        assertEquals(eventID, EVENT_ID);
    }

    @Test
    public void checkCorrectCpID() throws Exception {
        final String cpID = this.event.getCpID();
        assertEquals(cpID, CP_ID);
    }

}