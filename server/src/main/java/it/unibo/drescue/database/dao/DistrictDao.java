package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.District;

import java.util.List;

public interface DistrictDao {


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
     * Insert a given district into DB
     *
     * @param district to insert
     * @return true if the given district is added into DB
     * false if the given district already exists in the DB
     */
    boolean insert(District district);

    /**
     * Delete a district from DB
     *
     * @param district to delete
     * @return false if something goes wrong
     */
    boolean delete(District district);

    /**
     * Update a specific district with the new population given
     *
     * @param districtId represent the ID of the district to update
     * @param population represent the population to update in the specified districtID
     * @return false if districtID doesn't exist or something gone wrong
     */
    boolean update(String districtId, int population);

}
