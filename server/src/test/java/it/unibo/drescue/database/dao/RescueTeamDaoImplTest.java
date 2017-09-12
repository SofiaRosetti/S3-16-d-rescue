package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.exceptions.DBNotFoundRecordException;
import it.unibo.drescue.model.ObjectModel;
import it.unibo.drescue.model.RescueTeam;
import it.unibo.drescue.model.RescueTeamImplBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RescueTeamDaoImplTest extends GenericDaoAbstractTest {

    private static final String ID_TEST = "TestRescue";
    private static final String NAME_TEST = "Croce Gialla Test Ravenna";
    private static final String PASSWORD_TEST = "password";
    private static final double LATITUDE_RA = 44.420826;
    private static final double LONGITUDE_RA = 11.912387;
    private static final String PHONE_NUMBER_TEST = "345345345";


    private static final RescueTeam RESCUE_TEAM_TEST = new RescueTeamImplBuilder()
            .setRescueTeamID(ID_TEST)
            .setName(NAME_TEST)
            .setPassword(PASSWORD_TEST)
            .setLatitude(LATITUDE_RA)
            .setLongitude(LONGITUDE_RA)
            .setPhoneNumber(PHONE_NUMBER_TEST)
            .createRescueTeamImpl();

    private RescueTeamDao rescueTeamDao = null;

    @Override
    protected GenericDao getDaoForTest(final DBConnection connection) throws Exception {
        return this.rescueTeamDao = (RescueTeamDao)
                connection.getDAO(DBConnection.Table.RESCUE_TEAM);
    }

    @Override
    protected ObjectModel getTestObject() {
        return RESCUE_TEAM_TEST;
    }

    @Override
    protected void doOtherSetUp(final DBConnection connectionForTest) {
        //DO NOTHING
    }

    @Override
    protected void doOtherTearDown() {
        //DO NOTHING
    }

    /**
     * Test update method functionality.
     * For rescue_team the update concerns the password
     */
    @Test
    public void isUpdatingPassword() throws Exception {

        RescueTeam rescueTeamInDb =
                (RescueTeam) this.rescueTeamDao.insertAndGet(RESCUE_TEAM_TEST);

        assertEquals(rescueTeamInDb.getPassword(), RESCUE_TEAM_TEST.getPassword());

        final String newPassword = "password2";

        final RescueTeam rescueTeamToUpdate = new RescueTeamImplBuilder()
                .setRescueTeamID(rescueTeamInDb.getRescueTeamID())
                .setPassword(newPassword)
                .createRescueTeamImpl();

        this.rescueTeamDao.update(rescueTeamToUpdate);
        rescueTeamInDb = (RescueTeam)
                this.rescueTeamDao.selectByIdentifier(RESCUE_TEAM_TEST);
        assertEquals(rescueTeamInDb.getPassword(), newPassword);

        //Deleting civil protection test
        this.rescueTeamDao.delete(RESCUE_TEAM_TEST);
    }

    /**
     * Test login functionality.
     * Verify that an unregistered rescue team couldn't login
     */
    @Test
    public void isRejectingUnregisteredRescueTeam() throws Exception {
        final RescueTeam rescueTeamNotRegister = RESCUE_TEAM_TEST;
        rescueTeamNotRegister.setRescueTeamID("1");
        try {
            this.rescueTeamDao.login(rescueTeamNotRegister);
        } catch (final DBNotFoundRecordException e) {
            assertNotNull(e);
        }
    }

    /**
     * Test login functionality.
     * Verify that a registered rescue team could login with right credentials
     */
    @Test
    public void isLoggingInRegisteredRescueTeam() throws Exception {
        this.rescueTeamDao.insert(RESCUE_TEAM_TEST);
        assertNotNull(this.rescueTeamDao.login(RESCUE_TEAM_TEST));
        //Deleting test rescue team
        this.rescueTeamDao.delete(RESCUE_TEAM_TEST);
    }

}