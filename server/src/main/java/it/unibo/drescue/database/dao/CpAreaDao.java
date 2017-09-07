package it.unibo.drescue.database.dao;

import it.unibo.drescue.model.CpArea;

import java.util.List;

public interface CpAreaDao extends GenericDao {

    /**
     * Get all cp_areas
     *
     * @return a list with all cp_areas
     */
    List<CpArea> findAll();
}
