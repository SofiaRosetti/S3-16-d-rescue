package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CpEnrollmentImplTest {

    private static final String CP_ID = "FC1421";
    private static final String RESCUETEAM_ID = "Team001";
    private CpEnrollment enrollment;

    @Before
    public void createEnrollment() throws Exception {
        this.enrollment = new CpEnrollmentImpl(CP_ID, RESCUETEAM_ID);
    }

    @Test
    public void checkCorrectCpID() throws Exception {
        final String cpID = this.enrollment.getCpID();
        assertEquals(cpID, CP_ID);
    }

    @Test
    public void checkCorrectTeamID() throws Exception {
        final String rescueTeamID = this.enrollment.getRescueTeamID();
        assertEquals(rescueTeamID, RESCUETEAM_ID);
    }

}