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
     * Sets the civil protection ID
     *
     * @param cpID the civil protection ID
     */
    void setCpID(String cpID);

    /**
     * @return the district ID
     */
    String getDistrictID();

    /**
     * Sets the district ID
     *
     * @param districtID the district ID
     */
    void setDistrictID(String districtID);

}
