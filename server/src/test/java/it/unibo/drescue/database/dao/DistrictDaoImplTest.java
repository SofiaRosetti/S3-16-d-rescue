package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.database.DBConnectionImpl;
import it.unibo.drescue.model.District;
import it.unibo.drescue.model.DistrictImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DistrictDaoImplTest {

    private static final District DISTRICT_TEST =
            new DistrictImpl("TT", "Test-Test", 300);

    private DistrictDao districtDao = null;
    private DBConnection dbConnection;

    @Before
    public void setUp() throws Exception {
        this.dbConnection = DBConnectionImpl.getLocalConnection();
        //Initialize DistrictDao
        this.districtDao = (DistrictDao) this.dbConnection.getDAO(DBConnection.Table.DISTRICT);
    }

    @Test
    public void isInsertingAndDeletingDistrict() throws Exception {
        this.districtDao.insert(this.DISTRICT_TEST);
        assertTrue(this.districtDao.findById(this.DISTRICT_TEST.getDistrictID()) != null);
        this.districtDao.delete(this.DISTRICT_TEST);
        assertTrue(this.districtDao.findById(this.DISTRICT_TEST.getDistrictID()) == null);
    }

    @Test
    public void isFindingAllDistricts() throws Exception {
        final int initialSize = this.districtDao.findAll().size();
        this.districtDao.insert(this.DISTRICT_TEST);
        assertTrue(this.districtDao.findAll().size() == initialSize + 1);
        //Deleting test district
        this.districtDao.delete(this.DISTRICT_TEST);
        assertTrue(this.districtDao.findAll().size() == initialSize);
    }

    @Test
    public void isUpdatingPopulation() throws Exception {
        this.districtDao.insert(this.DISTRICT_TEST);
        final int populationBefore = this.districtDao.findById(this.DISTRICT_TEST.getDistrictID()).getPopulation();
        final int newPopulation = 500;
        assertTrue(populationBefore != newPopulation);
        this.districtDao.update(this.DISTRICT_TEST.getDistrictID(), newPopulation);
        final int populationAfter = this.districtDao.findById(this.DISTRICT_TEST.getDistrictID()).getPopulation();
        assertTrue(populationAfter == newPopulation);
        //Deleting test district
        this.districtDao.delete(this.DISTRICT_TEST);
    }


}