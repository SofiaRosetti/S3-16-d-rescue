package it.unibo.drescue.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GeocodingTest {

    private static final double LATITUDE = 44.420826;
    private static final double LONGITUDE = 11.912387;
    private static final String DISTRICT = "RA";
    private Geocoding geocoding;

    @Before
    public void setUp() throws Exception {
        this.geocoding = new Geocoding(this.LATITUDE, this.LONGITUDE);
    }

    @Test
    public void isDistrictCorrect() throws Exception {
        assertTrue(this.geocoding.getDistrict().equals(this.DISTRICT));
    }

}
