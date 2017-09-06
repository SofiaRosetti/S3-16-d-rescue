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
        this.dbConnection = DBConnectionImpl.getRemoteConnection();
        //Initialize DistrictDao
        this.districtDao = (DistrictDao) this.dbConnection.getDAO(DBConnection.Table.DISTRICT);
    }

    @Test
    public void isInsertingAndDeletingDistrict() throws Exception {
        this.districtDao.insert(DISTRICT_TEST);
        assertTrue(this.districtDao.selectByIdentifier(DISTRICT_TEST) != null);
        this.districtDao.delete(DISTRICT_TEST);
        assertTrue(this.districtDao.selectByIdentifier(DISTRICT_TEST) == null);
    }

    @Test
    public void isFindingAllDistricts() throws Exception {
        final int initialSize = this.districtDao.findAll().size();
        this.districtDao.insert(DISTRICT_TEST);
        assertTrue(this.districtDao.findAll().size() == initialSize + 1);
        //Deleting test district
        this.districtDao.delete(DISTRICT_TEST);
        assertTrue(this.districtDao.findAll().size() == initialSize);
    }

    @Test
    public void isUpdatingPopulation() throws Exception {
        this.districtDao.insert(DISTRICT_TEST);
        District districtInDb = (District) this.districtDao.selectByIdentifier(this.DISTRICT_TEST);
        final int populationBefore = districtInDb.getPopulation();
        final int newPopulation = 500;
        final District districtToUpdate = new DistrictImpl(
                DISTRICT_TEST.getDistrictID(), null, newPopulation);
        assertTrue(populationBefore != newPopulation);
        this.districtDao.update(districtToUpdate);
        districtInDb = (District) this.districtDao.selectByIdentifier(this.DISTRICT_TEST);
        final int populationAfter = districtInDb.getPopulation();
        assertTrue(populationAfter == newPopulation);
        //Deleting test district
        this.districtDao.delete(this.DISTRICT_TEST);
    }


}