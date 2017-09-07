package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DistrictImplTest {

    private static final String DISTRICT_ID = "FC";
    private static final String DISTRICT_LONGNAME = "Forlì-Cesena";
    private static final int POPULATION = 398322;
    private District district;

    @Before
    public void createDistrict() throws Exception {
        this.district = new DistrictImpl(DISTRICT_ID, DISTRICT_LONGNAME, POPULATION);
    }

    @Test
    public void checkCorrectID() throws Exception {
        final String districtID = this.district.getDistrictID();
        assertEquals(districtID, DISTRICT_ID);
    }

    @Test
    public void checkCorrectLongName() throws Exception {
        final String districtLongName = this.district.getDistrictLongName();
        assertEquals(districtLongName, DISTRICT_LONGNAME);
    }

    @Test
    public void checkCorrectPopulation() throws Exception {
        final int population = this.district.getPopulation();
        assertEquals(population, POPULATION);
    }

}