package it.unibo.drescue.model;

/**
 * An interface modelling the enrollment of a Rescue Team
 * in a Civil Protection.
 */
public interface CpEnrollment {

    /**
     * @return the civil protection ID
     */
    String getCpID();

    /**
     * @return the rescue team ID
     */
    String getRescueTeamID();
}
