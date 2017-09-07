package it.unibo.drescue.model;

/**
 * An interface modelling a Civil Protection area.
 */
public interface CpArea extends ObjectModel {

    /**
     * @return the civil protection ID
     */
    String getCpID();

    /**
     * @return the district ID
     */
    String getDistrictID();

}
