package it.unibo.mobileuser.gps;

/**
 * An interface modelling an activity for GPS.
 */
public interface GpsActivity {

    /**
     * Gets the latitude value.
     *
     * @return the latitude value.
     */
    String getLatitude();

    /**
     * Sets the latitude value.
     *
     * @param latitude the latitude value to set.
     */
    void setLatitude(String latitude);

    /**
     * Gets the longitude value.
     *
     * @return the longitude value.
     */
    String getLongitude();

    /**
     * Sets the longitude value.
     *
     * @param longitude the longitude value to set.
     */
    void setLongitude(String longitude);

}
