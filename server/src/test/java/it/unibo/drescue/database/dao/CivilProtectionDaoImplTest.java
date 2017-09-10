package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.model.CivilProtection;
import it.unibo.drescue.model.CivilProtectionImpl;
import it.unibo.drescue.model.ObjectModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class CivilProtectionDaoImplTest extends GenericDaoAbstractTest {

    private static final CivilProtection CIVIL_PROTECTION_TEST =
            new CivilProtectionImpl("TEST001", "password");

    private CivilProtectionDao civilProtectionDao = null;

    @Override
    protected GenericDao getDaoForTest(final DBConnection connection) throws Exception {
        return this.civilProtectionDao = (CivilProtectionDao)
                connection.getDAO(DBConnection.Table.CIVIL_PROTECTION);
    }

    @Override
    protected ObjectModel getTestObject() {
        return CIVIL_PROTECTION_TEST;
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
     * For civil protection the update concerns the password
     */
    @Test
    public void isUpdatingPassword() throws Exception {

        this.civilProtectionDao.insert(CIVIL_PROTECTION_TEST);

        final String newPassword = "password2";
        CivilProtection cpInDb = (CivilProtection)
                this.civilProtectionDao.selectByIdentifier(CIVIL_PROTECTION_TEST);
        assertEquals(cpInDb.getPassword(), CIVIL_PROTECTION_TEST.getPassword());
        final CivilProtection civilProtectionToUpdate = new CivilProtectionImpl(
                cpInDb.getCpID(),
                newPassword
        );

        this.civilProtectionDao.update(civilProtectionToUpdate);
        cpInDb = (CivilProtection)
                this.civilProtectionDao.selectByIdentifier(CIVIL_PROTECTION_TEST);
        assertEquals(cpInDb.getPassword(), newPassword);

        //Deleting civil protection test
        this.civilProtectionDao.delete(CIVIL_PROTECTION_TEST);
    }

    /**
     * Test login functionality.
     * Verify that an unregistered civil protection couldn't login
     */
    @Test
    public void isRejectingUnregisteredCp() throws Exception {
        final CivilProtection cpNotRegistered = CIVIL_PROTECTION_TEST;
        cpNotRegistered.setCpID("1");
        assertNull(this.civilProtectionDao.login(cpNotRegistered));
    }

    /**
     * Test login functionality.
     * Verify that a registered civil protection could login with right credentials
     */
    @Test
    public void isLoggingInRegisteredCp() throws Exception {
        this.civilProtectionDao.insert(CIVIL_PROTECTION_TEST);
        assertNotNull(this.civilProtectionDao.login(CIVIL_PROTECTION_TEST));
        //Deleting test civil protection
        this.civilProtectionDao.delete(CIVIL_PROTECTION_TEST);
    }

}