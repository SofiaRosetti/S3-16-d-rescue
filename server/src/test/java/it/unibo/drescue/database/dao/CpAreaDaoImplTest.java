package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.DBConnectionImpl;
import it.unibo.drescue.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CpAreaDaoImplTest {
    private static final District DISTRICT_TEST =
            new DistrictImpl("TT", "DistrictTest", 0);
    private static final CivilProtection CP_TEST =
            new CivilProtectionImpl("TEST002", "password");
    /*
     * TODO use district and civil protection already inserted in DB
     */
    private DistrictDao districtDao = null;
    private CivilProtectionDao civilProtectionDao = null;
    private CpAreaDao cpAreaDao = null;
    private DBConnection dbConnection;
    private CpArea cpAreaTest;

    @Before
    public void setUp() throws Exception {
        this.dbConnection = DBConnectionImpl.getLocalConnection();
        this.dbConnection.openConnection();
        this.districtDao = (DistrictDao)
                this.dbConnection.getDAO(DBConnection.Table.DISTRICT);
        this.civilProtectionDao = (CivilProtectionDao)
                this.dbConnection.getDAO(DBConnection.Table.CIVIL_PROTECTION);
        this.cpAreaDao = (CpAreaDao)
                this.dbConnection.getDAO(DBConnection.Table.CP_AREA);
        //Insert a CP
        this.civilProtectionDao.insert(this.CP_TEST);
        //Insert a District
        this.districtDao.insert(this.DISTRICT_TEST);
        //Create CpArea for test
        this.cpAreaTest = new CpAreaImpl(
                this.CP_TEST.getCpID(), this.DISTRICT_TEST.getDistrictID());
        //Initialize CpAreaDao
        this.cpAreaDao = (CpAreaDao) this.dbConnection.getDAO(DBConnection.Table.CP_AREA);

    }

    @Test
    public void isInsertingAndDeletingCpArea() throws Exception {
        this.cpAreaDao.insert(this.cpAreaTest);
        assertTrue(this.cpAreaDao.selectByIdentifier(this.cpAreaTest) != null);
        this.cpAreaDao.delete(this.cpAreaTest);
        assertTrue(this.cpAreaDao.selectByIdentifier(this.cpAreaTest) == null);
    }

    @Test
    public void isFindingAllCpAreas() throws Exception {
        final int initialSize = this.cpAreaDao.findAll().size();
        this.cpAreaDao.insert(this.cpAreaTest);
        assertTrue(this.cpAreaDao.findAll().size() == initialSize + 1);
        //Deleting test cp_area
        this.cpAreaDao.delete(this.cpAreaTest);
        assertTrue(this.cpAreaDao.findAll().size() == initialSize);
    }

    @After
    public void tearDown() throws Exception {
        //Deleting all object used for test
        this.districtDao.delete(this.DISTRICT_TEST);
        this.civilProtectionDao.delete(this.CP_TEST);
        //Closing connection
        this.dbConnection.closeConnection();
    }
}