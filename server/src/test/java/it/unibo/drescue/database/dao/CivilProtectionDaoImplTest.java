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
     * TODO
     *
     * @throws Exception
     */
    @Test
    public void isUpdatingPassword() throws Exception {
        final String newPassword = "password2";
        this.civilProtectionDao.insert(CIVIL_PROTECTION_TEST);
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
     * TODO
     *
     * @throws Exception
     */
    @Test
    public void isRejectingUnregisteredCp() throws Exception {
        assertFalse(this.civilProtectionDao.login(
                CIVIL_PROTECTION_TEST.getCpID(), CIVIL_PROTECTION_TEST.getPassword()));
    }

    /**
     * TODO
     *
     * @throws Exception
     */
    @Test
    public void isLoggingInRegisteredCp() throws Exception {
        this.civilProtectionDao.insert(CIVIL_PROTECTION_TEST);
        assertTrue(this.civilProtectionDao.login(
                CIVIL_PROTECTION_TEST.getCpID(), CIVIL_PROTECTION_TEST.getPassword()));
        //Deleting test user
        this.civilProtectionDao.delete(CIVIL_PROTECTION_TEST);
    }

}