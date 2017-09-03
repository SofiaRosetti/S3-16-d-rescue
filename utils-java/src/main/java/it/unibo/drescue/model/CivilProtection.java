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
     * @return the civil protection password
     */
    String getPassword();

}
