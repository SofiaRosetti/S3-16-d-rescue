package it.unibo.drescue.database;

import org.junit.Test;

import static org.junit.Assert.*;

public class DBConnectionImplTest {

    @Test
    public void isOpeningAndClosingRemoteConnection() {
        this.testConnectionWithAddress(
                DBConnectionImpl.getRemoteConnection(),
                DBConnectionImpl.REMOTE_ADDRESS);
    }

    private void testConnectionWithAddress(
            final DBConnectionImpl connection, final String address) {
        final DBConnectionImpl dbConnection;
        dbConnection = connection;
        dbConnection.openConnection();
        assertTrue(dbConnection.isValid());
        assertEquals(dbConnection.getDbAddress(), address);
        dbConnection.closeConnection();
        assertFalse(dbConnection.isValid());
    }

}