package it.unibo.drescue.model;

/**
 * An interface modelling the enrollment of a Rescue Team
 * in a Civil Protection.
 */
public interface CpEnrollment extends ObjectModel {

    /**
     * @return the civil protection ID
     */
    String getCpID();

    /**
     * Sets the civil protection ID
     *
     * @param cpID the civil protection ID
     */
    void setCpID(String cpID);

    /**
     * @return the rescue team ID
     */
    String getRescueTeamID();

    /**
     * Sets the rescue team ID
     *
     * @param rescueTeamID the rescue team ID
     */
    void setRescueTeamID(String rescueTeamID);
}
