package it.unibo.drescue.model;

/**
 * An interface modelling a District.
 */

public interface District extends ObjectModel {

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

    /**
     * @return the district long name
     */
    String getDistrictLongName();

    /**
     * Sets the district long name
     *
     * @param districtLongName the district long name
     */
    void setDistrictLongName(String districtLongName);

    /**
     * @return the district population
     */
    int getPopulation();

    /**
     * Sets the district population
     *
     * @param population the district population
     */
    void setPopulation(int population);
}
