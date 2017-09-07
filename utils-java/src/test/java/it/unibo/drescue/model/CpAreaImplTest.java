package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CpAreaImplTest {

    private static final String CP_ID = "FC1421";
    private static final String DISTRICT_ID = "FC";
    private CpArea cpArea;

    @Before
    public void createCpArea() throws Exception {
        this.cpArea = new CpAreaImpl(CP_ID, DISTRICT_ID);
    }

    @Test
    public void checkCorrectID() throws Exception {
        final String cpID = this.cpArea.getCpID();
        assertEquals(cpID, CP_ID);
    }

    @Test
    public void checkCorrectDistrictID() throws Exception {
        final String districtID = this.cpArea.getDistrictID();
        assertEquals(districtID, DISTRICT_ID);
    }

}