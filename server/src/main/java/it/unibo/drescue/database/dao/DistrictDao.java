package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.District;

import java.util.List;

public interface DistrictDao extends UpdatableDao {

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

}
