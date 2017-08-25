package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DistrictImplTest {

    private static final String DISTRICT_ID = "FC";
    private static final String DISTRICT_LONGNAME = "Forlì-Cesena";
    private static final int POPULATION = 398322;
    private District district;

    @Before
    public void createDistrict() throws Exception {
        this.district = new DistrictImpl(this.DISTRICT_ID, this.DISTRICT_LONGNAME, this.POPULATION);
    }

    @Test
    public void checkCorrectID() throws Exception {
        final String districtID = this.district.getDistrictID();
        assertTrue(districtID.equals(this.DISTRICT_ID));
    }

    @Test
    public void checkCorrectLongName() throws Exception {
        final String districtLongName = this.district.getDistrictLongName();
        assertTrue(districtLongName.equals(this.DISTRICT_LONGNAME));
    }

    @Test
    public void checkCorrectPopulation() throws Exception {
        final int population = this.district.getPopulation();
        assertTrue(population == this.POPULATION);
    }

}