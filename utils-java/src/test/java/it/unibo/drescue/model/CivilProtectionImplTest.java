package it.unibo.drescue.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CivilProtectionImplTest {

    private static final String CP_ID = "FC1421";
    private static final String PASSWORD = "ciyd33d8";
    private CivilProtection civilProtection;

    @Before
    public void createCivilProtection() throws Exception {
        this.civilProtection = new CivilProtectionImpl(CP_ID, PASSWORD);
    }

    @Test
    public void checkCorrectID() throws Exception {
        final String cpID = this.civilProtection.getCpID();
        assertEquals(cpID, CP_ID);
    }

    @Test
    public void checkCorrectPassword() throws Exception {
        final String password = this.civilProtection.getPassword();
        assertEquals(password, PASSWORD);
    }

}