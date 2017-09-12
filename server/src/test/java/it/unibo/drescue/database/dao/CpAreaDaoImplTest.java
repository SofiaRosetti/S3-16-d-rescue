package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.exceptions.DBNotFoundRecordException;
import it.unibo.drescue.database.exceptions.DBQueryException;
import it.unibo.drescue.model.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CpAreaDaoImplTest extends GenericDaoAbstractTest {
    private static final District DISTRICT_TEST =
            new DistrictImpl("TT", "DistrictTest", 0);
    private static final CivilProtection CP_TEST =
            new CivilProtectionImpl("TEST002", "password");

    //Create CpArea for test
    private static final CpArea cpAreaTest =
            new CpAreaImpl(CP_TEST.getCpID(), DISTRICT_TEST.getDistrictID());

    private DistrictDao districtDao = null;
    private CivilProtectionDao civilProtectionDao = null;
    private CpAreaDao cpAreaDao = null;


    @Override
    protected GenericDao getDaoForTest(final DBConnection connectionForTest) throws Exception {
        return this.cpAreaDao = (CpAreaDao) connectionForTest.getDAO(DBConnection.Table.CP_AREA);
    }

    @Override
    protected ObjectModel getTestObject() {
        return cpAreaTest;
    }

    @Override
    protected void doOtherSetUp(final DBConnection connectionForTest) throws Exception {
        //Get other util dao
        this.districtDao = (DistrictDao)
                connectionForTest.getDAO(DBConnection.Table.DISTRICT);
        this.civilProtectionDao = (CivilProtectionDao)
                connectionForTest.getDAO(DBConnection.Table.CIVIL_PROTECTION);
        /*
         * TODO use district and civil protection already inserted in DB
         */
        //Insert util records
        this.civilProtectionDao.insert(CP_TEST);
        this.districtDao.insert(DISTRICT_TEST);
    }

    @Override
    public void doOtherTearDown() throws DBQueryException, DBNotFoundRecordException {
        //Deleting all object used for test
        this.districtDao.delete(DISTRICT_TEST);
        this.civilProtectionDao.delete(CP_TEST);
    }

    /**
     * Test findAll functionality.
     */
    @Test
    public void isFindingAllCpAreas() throws Exception {
        final int initialSize = this.cpAreaDao.findAll().size();
        this.cpAreaDao.insert(cpAreaTest);
        assertEquals(this.cpAreaDao.findAll().size(), initialSize + 1);
        //Deleting test cp_area
        this.cpAreaDao.delete(cpAreaTest);
        assertEquals(this.cpAreaDao.findAll().size(), initialSize);
    }

    @Test
    public void isFindingCpAreaGivenADistrict() throws Exception {
        this.cpAreaDao.insert(cpAreaTest);
        final List<CpArea> cpAreasOfDistrict =
                this.cpAreaDao.findCpAreasByDistrict(DISTRICT_TEST.getDistrictID());
        assertNotNull(cpAreasOfDistrict);
        assertTrue(cpAreasOfDistrict.size() > 0);
        this.cpAreaDao.delete(cpAreaTest);
    }

    @Test
    public void isFindingCpAreaGivenACp() throws Exception {
        this.cpAreaDao.insert(cpAreaTest);
        final List<CpArea> cpAreasOfCp = this.cpAreaDao.findCpAreasByCp(CP_TEST.getCpID());
        assertNotNull(cpAreasOfCp);
        assertTrue(cpAreasOfCp.size() > 0);
        this.cpAreaDao.delete(cpAreaTest);
    }

}