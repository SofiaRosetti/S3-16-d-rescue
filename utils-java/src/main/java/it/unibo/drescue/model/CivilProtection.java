package it.unibo.drescue.model;

/**
 * An interface modelling a Civil Protection.
 */

public interface CivilProtection extends LoggableModel {

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


}
