package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.exceptions.DBNotFoundRecordException;
import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CpEnrollmentDaoImplTest extends GenericDaoAbstractTest {
    private static final RescueTeam RESCUE_TEAM_TEST = new RescueTeamImplBuilder()
            .setRescueTeamID(RescueTeamDaoImplTest.ID_TEST)
            .setName(RescueTeamDaoImplTest.NAME_TEST)
            .setPassword(RescueTeamDaoImplTest.PASSWORD_TEST)
            .setLatitude(RescueTeamDaoImplTest.LATITUDE_RA)
            .setLongitude(RescueTeamDaoImplTest.LONGITUDE_RA)
            .setPhoneNumber(RescueTeamDaoImplTest.PHONE_NUMBER_TEST)
            .createRescueTeamImpl();
    private static final CivilProtection CP_TEST =
            new CivilProtectionImpl("TEST003", "password");

    //Create cpEnrollment for test
    private static final CpEnrollment CP_ENROLLMENT_TEST =
            new CpEnrollmentImpl(CP_TEST.getCpID(), RESCUE_TEAM_TEST.getRescueTeamID());

    private RescueTeamDao rescueTeamDao = null;
    private CivilProtectionDao civilProtectionDao = null;
    private CpEnrollmentDao cpEnrollmentDao = null;


    @Override
    protected GenericDao getDaoForTest(final DBConnection connectionForTest) throws Exception {
        return this.cpEnrollmentDao = (CpEnrollmentDao) connectionForTest.getDAO(DBConnection.Table.CP_ENROLLMENT);
    }

    @Override
    protected ObjectModel getTestObject() {
        return CP_ENROLLMENT_TEST;
    }

    @Override
    protected void doOtherSetUp(final DBConnection connectionForTest) throws Exception {
        //Get other util dao
        this.rescueTeamDao = (RescueTeamDao)
                connectionForTest.getDAO(DBConnection.Table.RESCUE_TEAM);
        this.civilProtectionDao = (CivilProtectionDao)
                connectionForTest.getDAO(DBConnection.Table.CIVIL_PROTECTION);
        //Insert util records
        this.civilProtectionDao.insert(CP_TEST);
        this.rescueTeamDao.insert(RESCUE_TEAM_TEST);
    }

    @Override
    public void doOtherTearDown() throws DBQueryException, DBNotFoundRecordException {
        //Deleting all object used for test
        this.rescueTeamDao.delete(RESCUE_TEAM_TEST);
        this.civilProtectionDao.delete(CP_TEST);
    }

    @Test
    public void isFindingCpGivenARescueTeam() throws Exception {
        this.cpEnrollmentDao.insert(CP_ENROLLMENT_TEST);
        final List<CivilProtection> cpsOfRescueTeam =
                this.cpEnrollmentDao.findAllCpRelatedToARescueTeam(RESCUE_TEAM_TEST.getRescueTeamID());
        assertNotNull(cpsOfRescueTeam);
        assertTrue(cpsOfRescueTeam.size() > 0);
        this.cpEnrollmentDao.delete(CP_ENROLLMENT_TEST);
    }

    @Test
    public void isFindingRelatedAndNotRelatedRescueTeamsGivenACp() throws Exception {

        final List<RescueTeam> rescueTeamsNotEnrolledByCp =
                this.cpEnrollmentDao.findAllRescueTeamGivenACp(CP_TEST.getCpID(), false);
        assertTrue(rescueTeamsNotEnrolledByCp.size() > 0);

        this.cpEnrollmentDao.insert(CP_ENROLLMENT_TEST);
        final List<RescueTeam> rescueTeamsEnrolledByCp =
                this.cpEnrollmentDao.findAllRescueTeamGivenACp(CP_TEST.getCpID(), true);
        assertTrue(rescueTeamsEnrolledByCp.size() > 0);

        this.cpEnrollmentDao.delete(CP_ENROLLMENT_TEST);
    }

}