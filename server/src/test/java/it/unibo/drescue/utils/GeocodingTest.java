package it.unibo.drescue.utils;

import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GeocodingTest {

    private static final double LATITUDE_RA = 44.420826;
    private static final double LONGITUDE_RA = 11.912387;
    private static final String DISTRICT = "RA";
    private static final String ADDRESS = "Via Sacchi 3 Cesena";
    private static final double LATITUDE_FC = 44.1397615;
    private static final double LONGITUDE_FC = 12.2432193;
    private Geocoding geocoding;

    @Before
    public void setUp() throws Exception {
        this.geocoding = new Geocoding();
    }

    @Test
    public void isDistrictCorrect() throws Exception {
        assertTrue(this.geocoding.getDistrict(this.LATITUDE_RA, this.LONGITUDE_RA).equals(this.DISTRICT));
    }

    @Test
    public void isLatLngCorrect() throws Exception {
        final JsonObject latlng = this.geocoding.getLatLng(this.ADDRESS);
        final double latitude = latlng.get("lat").getAsDouble();
        final double longitude = latlng.get("lng").getAsDouble();
        assertTrue(latitude == this.LATITUDE_FC && longitude == this.LONGITUDE_FC);
    }
}
