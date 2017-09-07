package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.DBConnectionImpl;
import it.unibo.drescue.model.CivilProtection;
import it.unibo.drescue.model.CivilProtectionImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CivilProtectionDaoImplTest {

    protected static final CivilProtection CIVIL_PROTECTION_TEST =
            new CivilProtectionImpl("TEST001", "password");

    private CivilProtectionDao civilProtectionDao = null;
    private DBConnection dbConnection;

    @Before
    public void setUp() throws Exception {
        this.dbConnection = DBConnectionImpl.getRemoteConnection();
        //Initialize EventDao
        this.civilProtectionDao = (CivilProtectionDao)
                this.dbConnection.getDAO(DBConnection.Table.CIVIL_PROTECTION);
    }

    @Test
    public void isInsertingAndDeletingCivilProtection() throws Exception {
        this.civilProtectionDao.insert(CIVIL_PROTECTION_TEST);
        assertTrue(this.civilProtectionDao.selectByIdentifier(CIVIL_PROTECTION_TEST) != null);
        this.civilProtectionDao.delete(CIVIL_PROTECTION_TEST);
        assertTrue(this.civilProtectionDao.selectByIdentifier(CIVIL_PROTECTION_TEST) == null);
    }

    @Test
    public void isUpdatingPassword() throws Exception {
        final String newPassword = "password2";
        this.civilProtectionDao.insert(CIVIL_PROTECTION_TEST);
        CivilProtection civilProtectionInDb = (CivilProtection)
                this.civilProtectionDao.selectByIdentifier(CIVIL_PROTECTION_TEST);
        assertTrue(civilProtectionInDb.getPassword().equals(CIVIL_PROTECTION_TEST.getPassword()));
        final CivilProtection civilProtectionToUpdate = new CivilProtectionImpl(
                civilProtectionInDb.getCpID(),
                newPassword
        );
        this.civilProtectionDao.update(civilProtectionToUpdate);
        civilProtectionInDb = (CivilProtection)
                this.civilProtectionDao.selectByIdentifier(CIVIL_PROTECTION_TEST);
        assertTrue(civilProtectionInDb.getPassword().equals(newPassword));
        //Deleting civil protection test
        this.civilProtectionDao.delete(CIVIL_PROTECTION_TEST);
    }


    @Test
    public void isRejectingUnregisteredCp() throws Exception {
        assertFalse(this.civilProtectionDao.login(
                CIVIL_PROTECTION_TEST.getCpID(), CIVIL_PROTECTION_TEST.getPassword()));
    }

    @Test
    public void isLoggingInRegisteredCp() throws Exception {
        this.civilProtectionDao.insert(CIVIL_PROTECTION_TEST);
        assertTrue(this.civilProtectionDao.login(
                CIVIL_PROTECTION_TEST.getCpID(), CIVIL_PROTECTION_TEST.getPassword()));
        //Deleting test user
        this.civilProtectionDao.delete(CIVIL_PROTECTION_TEST);
    }

    @After
    public void tearDown() throws Exception {
        this.dbConnection.closeConnection();
    }

}