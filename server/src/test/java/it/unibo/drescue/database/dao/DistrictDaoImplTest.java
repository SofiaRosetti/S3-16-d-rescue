package it.unibo.drescue.database.dao;

import it.unibo.drescue.database.DBConnection;
import it.unibo.drescue.model.District;
import it.unibo.drescue.model.DistrictImpl;
import it.unibo.drescue.model.ObjectModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DistrictDaoImplTest extends GenericDaoAbstractTest {

    private static final District DISTRICT_TEST =
            new DistrictImpl("TT", "Test-Test", 300);

    private DistrictDao districtDao = null;

    @Override
    protected GenericDao getDaoForTest(final DBConnection connectionForTest) throws Exception {
        return this.districtDao = (DistrictDao)
                connectionForTest.getDAO(DBConnection.Table.DISTRICT);
    }

    @Override
    protected ObjectModel getTestObject() {
        return DISTRICT_TEST;
    }

    @Override
    protected void doOtherSetUp(final DBConnection connectionForTest) throws Exception {
        //DO NOTHING
    }

    @Override
    protected void doOtherTearDown() {
        //DO NOTHING
    }


    /**
     * Test findAll functionality.
     */
    @Test
    public void isFindingAllDistricts() throws Exception {
        final int initialSize = this.districtDao.findAll().size();
        this.districtDao.insert(DISTRICT_TEST);
        assertEquals(this.districtDao.findAll().size(), initialSize + 1);
        //Deleting test district
        this.districtDao.delete(DISTRICT_TEST);
        assertEquals(this.districtDao.findAll().size(), initialSize);
    }

    /**
     * Test update method functionality.
     * For districts the update concerns the population
     */
    @Test
    public void isUpdatingPopulation() throws Exception {
        this.districtDao.insert(DISTRICT_TEST);

        District districtInDb = (District) this.districtDao.selectByIdentifier(DISTRICT_TEST);
        final int populationBefore = districtInDb.getPopulation();
        final int newPopulation = 500;

        final District districtToUpdate = new DistrictImpl(
                DISTRICT_TEST.getDistrictID(), null, newPopulation);

        assertNotEquals(populationBefore, newPopulation);

        this.districtDao.update(districtToUpdate);
        districtInDb = (District) this.districtDao.selectByIdentifier(DISTRICT_TEST);
        final int populationAfter = districtInDb.getPopulation();

        assertEquals(populationAfter, newPopulation);

        //Deleting test district
        this.districtDao.delete(DISTRICT_TEST);
    }
}