package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.District;

import java.util.List;

public interface DistrictDao extends UpdatableDao {

    /**
     * Get all districts
     *
     * @return a list with all districts
     * @throws DBQueryException if something goes wrong executing the findAll query
     */
    List<District> findAll() throws DBQueryException;

}
