package it.unibo.drescue.model;

/**
 * An interface modelling a Rescue Team.
 */
public interface RescueTeam extends ObjectModel {

    /**
     * @return the rescue team ID
     */
    String getRescueTeamID();

    /**
     * @return the rescue team password
     */
    String getPassword();

    /**
     * @return the rescue team name
     */
    String getName();

    /**
     * @return the rescue team latitude
     */
    double getLatitude();

    /**
     * @return the rescue team longitude
     */
    double getLongitude();

    /**
     * @return the rescue team phone number
     */
    String getPhoneNumber();

}
