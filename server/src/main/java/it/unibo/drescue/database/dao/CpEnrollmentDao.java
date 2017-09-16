package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.CivilProtection;
import it.unibo.drescue.model.RescueTeam;

import java.util.List;

public interface CpEnrollmentDao extends GenericDao {

    /**
     * Get all civil protections related to a specified Rescue Team
     *
     * @return a list with all civil protection related to a specific rescue team
     * @throws DBQueryException if something goes wrong executing the query
     */
    List<CivilProtection> findAllCpRelatedToARescueTeam(String rescueTeamID) throws DBQueryException;

    /**
     * Get all rescue teams related to a specified civil protection
     *
     * @return a list with all rescue teams enrolled to the given cpID
     * @throws DBQueryException if something goes wrong executing the query
     */
    List<RescueTeam> findAllRescueTeamRelatedToACp(String cpID) throws DBQueryException;
}
