package it.unibo.drescue.model;

/**
 * An interface modelling a Rescue Team.
 */
public interface RescueTeam extends LoggableModel {

    /**
     * @return the rescue team ID
     */
    String getRescueTeamID();

    /**
     * Sets the rescue team ID
     *
     * @param rescueTeamID the team iD
     */
    void setRescueTeamID(String rescueTeamID);

    /**
     * @return the rescue team name
     */
    String getName();

    /**
     * Sets the rescue team name
     *
     * @param name the team name
     */
    void setName(String name);

    /**
     * @return the rescue team latitude
     */
    double getLatitude();

    /**
     * Sets the rescue team latitude
     *
     * @param latitude the team latitude
     */
    void setLatitude(double latitude);

    /**
     * @return the rescue team longitude
     */
    double getLongitude();

    /**
     * Sets the rescue team longitude
     *
     * @param longitude the team longitude
     */
    void setLongitude(double longitude);

    /**
     * @return the rescue team phone number
     */
    String getPhoneNumber();

    /**
     * Sets the rescue team phone number
     *
     * @param phoneNumber the team phone number
     */
    void setPhoneNumber(String phoneNumber);

    /**
     * Creates a string representation of the rescue team
     * useful to be included in civil protection views
     *
     * @return a string representing the rescue team
     */
    String toPrintableString();

}
