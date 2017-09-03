package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.District;

import java.util.List;

public interface DistrictDao extends GenericDao {

    /**
     * Find a specific district with the given id
     *
     * @param districtId specify the district to find
     * @return the district if a district with the given id exists
     */
    District findById(String districtId);

    /**
     * Get all districts
     *
     * @return a list with all districts
     */
    List<District> findAll();

    /**
     * Update a specific district with the new population given
     *
     * @param districtId represent the ID of the district to update
     * @param population represent the population to update in the specified districtID
     * @return false if districtID doesn't exist or something gone wrong
     */
    boolean update(String districtId, int population);

}
