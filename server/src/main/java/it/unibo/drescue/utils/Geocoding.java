package it.unibo.drescue.utils;

import com.google.gson.JsonObject;

/**
 * An interface modelling geocoding operations.
 */
public interface Geocoding {

    /**
     * Gets the short name of a district from its latitude and longitude
     *
     * @param latitude  the district latitude
     * @param longitude the district longitude
     * @return the district short name
     */
    String getDistrict(double latitude, double longitude) throws GeocodingException;

    /**
     * Gets latitude and longitude of a specified address
     *
     * @param address the address
     * @return a JsonObject containing the address latitude and longitude
     */
    JsonObject getLatLng(String address) throws GeocodingException;
}
