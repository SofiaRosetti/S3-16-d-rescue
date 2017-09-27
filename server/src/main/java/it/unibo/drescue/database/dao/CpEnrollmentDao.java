package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.CivilProtection;
import it.unibo.drescue.model.RescueTeam;

import java.util.List;

/**
 * Interface of DAO for CpEnrollment objects, it extends GenericDao
 * and specify some other useful methods for this objectModel
 */
public interface CpEnrollmentDao extends GenericDao {

    /**
     * Get all civil protections related to a specified Rescue Team.
     *
     * @param rescueTeamID rescue team identifier
     * @return a list with all civil protection related to a specific rescue team
     * @throws DBQueryException if something goes wrong executing the query
     */
    List<CivilProtection> findAllCpRelatedToARescueTeam(String rescueTeamID) throws DBQueryException;

    /**
     * Get all rescue teams related or not to a specified civil protection
     *
     * @param cpID    the cp involved
     * @param related specify if you want to find all teams enrolled to the specified cp (if true)
     *                or if you want to find all teams NOT enrolled yet to the specified cp (if false)
     * @return a list with all rescue teams enrolled to the given cpID
     * @throws DBQueryException if something goes wrong executing the query
     */
    List<RescueTeam> findAllRescueTeamGivenACp(String cpID, boolean related) throws DBQueryException;

}
