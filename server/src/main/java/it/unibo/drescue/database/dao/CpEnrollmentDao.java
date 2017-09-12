package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.CpEnrollment;

import java.util.List;

public interface CpEnrollmentDao extends GenericDao {

    /**
     * Get all cp_enrollments related to a specified Rescue Team
     *
     * @return a list with all cp_enrollments related to a specific rescue team
     * @throws DBQueryException if something goes wrong executing the query
     */
    List<CpEnrollment> findAllCpEnrollmentRelatedToARescueTeam(String rescueTeamID) throws DBQueryException;

    /**
     * Get all cp_enrollments related to a specified civil protection
     *
     * @return a list with all cp_enrollments with given cpID
     * @throws DBQueryException if something goes wrong executing the query
     */
    List<CpEnrollment> findAllCpEnrollmentRelatedToACp(String cpID) throws DBQueryException;
}
