package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.District;

import java.util.List;

public interface DistrictDao extends UpdatableDao {

    /**
     * Get all districts
     *
     * @return a list with all districts
     */
    List<District> findAll();

}
