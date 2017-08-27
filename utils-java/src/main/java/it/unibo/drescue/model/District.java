package it.unibo.drescue.model;

/**
 * An interface modelling a District.
 */

public interface District {

    /**
     * @return the district ID
     */
    String getDistrictID();

    /**
     * @return the district long name
     */
    String getDistrictLongName();

    /**
     * @return the district population
     */
    int getPopulation();
}
