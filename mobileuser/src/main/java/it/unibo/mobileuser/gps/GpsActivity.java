package it.unibo.mobileuser.gps;

/**
 * An interface modelling an activity for GPS.
 */
public interface GpsActivity {

    /**
     * @return the latitude value.
     */
    String getLatitude();

    /**
     * @param latitude the latitude value to set.
     */
    void setLatitude(String latitude);

    /**
     * @return the longitude value.
     */
    String getLongitude();

    /**
     * @param longitude the longitude value to set.
     */
    void setLongitude(String longitude);

}
