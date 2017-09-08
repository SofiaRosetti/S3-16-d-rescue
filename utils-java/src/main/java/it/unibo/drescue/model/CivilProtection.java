package it.unibo.drescue.model;

/**
 * An interface modelling a Civil Protection.
 */

public interface CivilProtection extends ObjectModel {

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
     * @return the civil protection password
     */
    String getPassword();

    /**
     * Sets the civil protection password
     *
     * @param password the civil protection password
     */
    void setPassword(String password);

}
