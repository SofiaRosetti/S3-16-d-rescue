package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.CpArea;

import java.util.List;

public interface CpAreaDao extends GenericDao {

    /**
     * Get all cp_areas
     *
     * @return a list with all cp_areas
     * @throws DBQueryException if something goes wrong executing the findAll query
     */
    List<CpArea> findAll() throws DBQueryException;

    /**
     * Get all cp_areas with given districtID
     *
     * @param districtID the id of the district of which you want to find areas
     * @return a list with all cp_areas with given districtID
     * @throws DBQueryException if something goes wrong executing the query
     */
    List<CpArea> findCpAreasByDistrict(String districtID) throws DBQueryException;

    /**
     * Get all cp_areas with given cpID
     *
     * @param cpID the id of the civil protection of which you want to find areas
     * @return a list with all cp_areas with given cpID
     * @throws DBQueryException if something goes wrong executing the query
     */
    List<CpArea> findCpAreasByCp(String cpID) throws DBQueryException;


}
